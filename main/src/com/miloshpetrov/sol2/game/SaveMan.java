package com.miloshpetrov.sol2.game;

import com.miloshpetrov.sol2.SolFiles;
import com.miloshpetrov.sol2.game.item.ItemMan;
import com.miloshpetrov.sol2.game.item.SolItem;
import com.miloshpetrov.sol2.game.ship.HullConfig;
import com.miloshpetrov.sol2.game.ship.HullConfigs;
import com.miloshpetrov.sol2.IniReader;

import java.util.ArrayList;

public class SaveMan {

  public static final String FILE_NAME = "prevShip.ini";

  public static void writeShip(HullConfig hull, float money, ArrayList<SolItem> items, SolGame game) {
    String hullName = game.getHullConfigs().getName(hull);
    StringBuilder sb = new StringBuilder();
    for (SolItem i : items) {
      sb.append(i.getCode()).append(" ");
    }
    IniReader.write(FILE_NAME, "hull", hullName, "money", (int) money, "items", sb.toString());
  }

  public static boolean hasPrevShip() {
    return SolFiles.writable(FILE_NAME).exists();
  }

  public static ShipConfig readShip(HullConfigs hullConfigs, ItemMan itemMan) {
    IniReader ir = new IniReader(FILE_NAME, null, false);
    String hullName = ir.s("hull", null);
    if (hullName == null) return null;
    HullConfig hull = hullConfigs.getConfig(hullName);
    if (hull == null) return null;
    int money = ir.i("money", 0);
    String itemsStr = ir.s("items", "");
    return new ShipConfig(hull, itemsStr, money, 1, null, itemMan);
  }
}
