package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
//import com.sun.org.apache.xml.internal.utils.ObjectPool;

public class TanksController extends ObjectPool<Tank> {
    private GameController gc;
    private Vector2 tmp;

    @Override
    protected Tank newObject() {
        return new Tank(gc);
    }

    public TanksController(GameController gc) {
        this.gc = gc;
        this.tmp = new Vector2();
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void setup(float x, float y, Tank.Owner ownerType) {
        Tank t = activateObject();
        t.setup(ownerType, x, y);
    }

    public Tank getNearestAiTank(Vector2 point) {
        for (int i = 0; i < activeList.size(); i++) {
            Tank t = activeList.get(i);
            if (t.getOwnerType() == Tank.Owner.AI && t.getPosition().dst(point) < 30) {
                return t;
            }
        }
        return null;
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        playerUpdate(dt);
        aiUpdate(dt);
        checkPool();
    }

    public void playerUpdate(float dt) { //реакции на действия игрока.
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            for (int i = 0; i < gc.getSelectedUnits().size(); i++) {
                Tank t = gc.getSelectedUnits().get(i);
                if (t.getOwnerType() == Tank.Owner.PLAYER && gc.getSelectedUnits().contains(t)) {
                    tmp.set(Gdx.input.getX(), 720 - Gdx.input.getY());
                    if (t.getWeapon().getType() == Weapon.Type.HARVEST) {//если харвестер едь в tmp
                        t.commandMoveTo(tmp);
                    }
                    if (t.getWeapon().getType() == Weapon.Type.GROUND) {//если наземный
                        Tank aiTank = gc.getTanksController().getNearestAiTank(tmp);
                        if (aiTank == null) { //клик по земле едь
                            t.commandMoveTo(tmp);
                            t.target=null; //чтобы прервать атаку танков
                        } else { //если чужой танк подъедь и атакуй
                            t.commandAttack(aiTank); //танк залипает в атаке и не едет в указаную точку если указать

                        }
                    }
                }
            }
        }
    }

    public void aiUpdate(float dt) { //реакции на действия AI.

    }
}