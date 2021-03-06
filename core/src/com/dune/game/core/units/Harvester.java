package com.dune.game.core.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.*;
import com.dune.game.core.interfaces.Targetable;
import com.dune.game.core.units.types.Owner;
import com.dune.game.core.units.types.UnitType;
import com.dune.game.core.users_logic.BaseLogic;
import com.dune.game.screens.utils.Assets;

import static com.dune.game.core.units.types.Owner.AI;
import static com.dune.game.core.units.types.Owner.PLAYER;

public class Harvester extends AbstractUnit {

    public Harvester(GameController gc) {
        super(gc);
        this.textures = Assets.getInstance().getAtlas().findRegion("tankcore").split(64, 64)[0];
        this.weaponTexture = Assets.getInstance().getAtlas().findRegion("harvester");
        this.containerCapacity = 10;
        this.minDstToActiveTarget = 5.0f;
        this.speed = 120.0f;
        this.weapon = new Weapon(4.0f, 1);
        this.hpMax = 500;
        this.unitType = UnitType.HARVESTER;
    }

    @Override
    public void setup(BaseLogic baseLogic, float x, float y) {
        this.position.set(x, y);
        this.baseLogic = baseLogic;
        this.ownerType = baseLogic.getOwnerType();
        this.hp = this.hpMax;
        this.destination = new Vector2(position);
    }

    public void updateWeapon(float dt) {

        if (gc.getMap().getResourceCount(position) > 0 && container<containerCapacity){
            int result = weapon.use(dt);

            if (result > -1) {
                container += gc.getMap().harvestResource(position, result);
                //destination.set(getPosition().x,getPosition().y);
                if (container == containerCapacity) {
                    if(getOwnerType()==PLAYER){
                        destination.set(200, 150); //после наполнения контейнера отправляется на базу
                    }
                    if(getOwnerType()==AI){
                        destination.set(875, 450); //после наполнения контейнера отправляется на базу
                    }

                }

            }


        } else {

            weapon.reset();
        }


    }

    @Override
    public void commandAttack(Targetable target) {
        commandMoveTo(target.getPosition());
    }

    @Override
    public void renderGui(SpriteBatch batch) {
        super.renderGui(batch);
        if (weapon.getUsageTimePercentage() > 0.0f) {
            destination.set(getPosition().x,getPosition().y);
            batch.setColor(0.2f, 0.2f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 32, position.y + 22, 64, 8);
            batch.setColor(1.0f, 1.0f, 0.0f, 1.0f);
            batch.draw(progressbarTexture, position.x - 30, position.y + 24, 60 * weapon.getUsageTimePercentage(), 4);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public void update(float dt) {
        super.update(dt);
        if(!(container==containerCapacity)){
            destination.set((float) (Math.random()*1400),(float) (Math.random()*900));
        }

        Building b = gc.getMap().getBuildingEntrance(getCellX(), getCellY());
        if (b != null && b.getType() == Building.Type.STOCK && b.getOwnerLogic() == this.baseLogic) {
            baseLogic.addMoney(container * 100);
            container = 0;
            //destination.set((float) (Math.random()*1400),(float) (Math.random()*900)); //после выгрузки рандомно отправляется на поле
        }
    }
}