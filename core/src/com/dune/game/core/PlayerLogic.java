package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dune.game.core.units.*;

public class PlayerLogic {
    public static int money;
    //private static int money;
    private GameController gc;

    //protected int money;
    private int unitsCount;
    private int unitsMaxCount;

    public int getMoney() {
        return money;
    }


    public int getUnitsCount() {
        return unitsCount;
    }

    public int getUnitsMaxCount() {
        return unitsMaxCount;
    }

    public PlayerLogic(GameController gc) {
        this.gc = gc;
        this.money = 0;
        this.unitsCount = 0;
        this.unitsMaxCount = 100;
    }

    public void update(float dt) {
//        for (int i = 0; i < gc.getSelectedUnits().size(); i++) {
//            AbstractUnit u = gc.getSelectedUnits().get(i);
//            if(u.position.x<200 && u.position.y<200){
//                money += u.getContainer();
//                u.setContainer(0);
//            }
//        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            for (int i = 0; i < gc.getSelectedUnits().size(); i++) {
                AbstractUnit u = gc.getSelectedUnits().get(i);

                if (u.getOwnerType() == Owner.PLAYER) {
                    unitProcessing(u);
                }
            }
        }
    }

    public void unitProcessing(AbstractUnit unit) {
        if (unit.getUnitType() == UnitType.HARVESTER) {
            unit.commandMoveTo(gc.getMouse());
            return;
        }
        if (unit.getUnitType() == UnitType.BATTLE_TANK) {
            AbstractUnit aiUnit = gc.getUnitsController().getNearestAiUnit(gc.getMouse());
            if (aiUnit == null) {
                unit.commandMoveTo(gc.getMouse());
            } else {
                unit.commandAttack(aiUnit);
            }
        }
    }
}