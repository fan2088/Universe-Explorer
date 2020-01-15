package com.xode.spaceTrader.model;

import com.xode.spaceTrader.util.Calculator;
import com.xode.spaceTrader.util.FixedRandom;
import com.xode.spaceTrader.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Police extends NPC {
    private static final int MAX_STOLEN_COUNT = 2;

    private ArrayList<Goods> stolenItems;

    public Police(Game game) {
        Player player = game.getPlayer();
        ArrayList<Goods> currentCargoes = player.getShip().getCargoes();
        int numberOfStolenItems = Math.min(currentCargoes.size(),
                FixedRandom.nextInt(MAX_STOLEN_COUNT) + 1);
        ArrayList<Integer> randomIndices = Util.randomSelect(currentCargoes, numberOfStolenItems);
        stolenItems = new ArrayList<>();
        for (int index: randomIndices) {
            stolenItems.add(currentCargoes.get(index));
        }
    }

    public String stolenItemsList() {
        StringBuilder s = new StringBuilder();
        for (Goods good: stolenItems) {
            s.append(good.toString()).append(" ");
        }
        return s.toString();
    }

    public List<Goods> takeItems(Player player) {
        Ship ship = player.getShip();
        List<Goods> taken = new ArrayList<>();
        for (Goods stolenItem: stolenItems) {
            taken.add(ship.removeCargo(stolenItem));
        }
        return taken;
    }

    public int giveFine(Player player) {
        int fine = Calculator.getFineAmount(stolenItems);
        player.setCredits(player.getCredits() - fine);
        return fine;
    }

    public String toString() {
        return "Police, demand items: " + stolenItems.toString() + ".";
    }

    public ArrayList<Goods> getStolenItems() {
        return stolenItems;
    }

    @Override
    public String getNpcType() {
        return "Police";
    }
}
