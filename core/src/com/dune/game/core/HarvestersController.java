package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.BattleTank;
import com.dune.game.core.units.Harvester;
import com.dune.game.core.units.Owner;

import static com.dune.game.core.units.Owner.PLAYER;

//import java.awt.datatransfer.MimeTypeParameterList;

public class HarvestersController extends ObjectPool<Harvester> {
    //public static MimeTypeParameterList activeList;
    private GameController gc;

    @Override
    protected Harvester newObject() {
        return new Harvester(gc);
    }

    public HarvestersController(GameController gc) {
        this.gc = gc;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void setup(float x, float y, Owner ownerType) {
        Harvester t = activateObject();
        t.setup(ownerType, x, y);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            if(Math.abs(activeList.get(i).position.x-180)<80 && Math.abs(activeList.get(i).position.y-180)<80 && activeList.get(i).getOwnerType()==PLAYER){
                PlayerLogic.money += activeList.get(i).getContainer();
                activeList.get(i).setContainer(0);
            }
            activeList.get(i).update(dt);


        }
         checkPool();
    }
}