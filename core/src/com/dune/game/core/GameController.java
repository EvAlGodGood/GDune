package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameController {
    private BattleMap map;
    private ProjectilesController projectilesController;
    private TanksController tanksController;
    private Vector2 tmp;
    private Vector2 selectionStart;

    private List<Tank> selectedUnits;

    public TanksController getTanksController() {
        return tanksController;
    }

    public List<Tank> getSelectedUnits() {
        return selectedUnits;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public BattleMap getMap() {
        return map;
    }

    // Инициализация игровой логики
    public GameController() {
        Assets.getInstance().loadAssets();
        this.tmp = new Vector2();
        this.selectionStart = new Vector2();
        this.selectedUnits = new ArrayList<>();
        this.map = new BattleMap();
        this.projectilesController = new ProjectilesController(this);
        this.tanksController = new TanksController(this);
        for (int i = 0; i < 5; i++) {
            this.tanksController.setup(MathUtils.random(80, 1200), MathUtils.random(80, 640), Tank.Owner.PLAYER);
        }
        for (int i = 0; i < 2; i++) {
            this.tanksController.setup(MathUtils.random(80, 1200), MathUtils.random(80, 640), Tank.Owner.AI);
        }
        prepareInput();
    }

    public void update(float dt) {
        tanksController.update(dt);
        projectilesController.update(dt);
        map.update(dt);
        checkCollisions(dt);
        checkHit(dt);
        // checkSelection();
    }

    public void checkCollisions(float dt) {
        for (int i = 0; i < tanksController.activeSize() - 1; i++) {
            Tank t1 = tanksController.getActiveList().get(i);
            for (int j = i + 1; j < tanksController.activeSize(); j++) {
                Tank t2 = tanksController.getActiveList().get(j);
                float dst = t1.getPosition().dst(t2.getPosition());
                if (dst < 30 + 30) {
                    float colLengthD2 = (60 - dst) / 2;
                    tmp.set(t2.getPosition()).sub(t1.getPosition()).nor().scl(colLengthD2);
                    t2.moveBy(tmp);
                    tmp.scl(-1);
                    t1.moveBy(tmp);
                }
            }
        }
    }

    public void checkHit(float dt) { //проверка попадания
        for (int i = 0; i < tanksController.activeSize(); i++) {
            Tank t2 = tanksController.getActiveList().get(i);
            for (int j = 0; j< projectilesController.activeSize(); j++){
                Projectile p2 = projectilesController.getActiveList().get(j);
                if (Math.abs(t2.position.x - p2.position.x)<30 && Math.abs(t2.position.y - p2.position.y)<30 && t2.getOwnerType().equals(Tank.Owner.AI)){
                    t2.setHp(t2.getHp()-30);
                    p2.position.x=-30; //выброс снаряда за поле для удаления
                    if(!t2.isActive()){
                        for (int t = 0; t < tanksController.activeSize(); t++) { //сброс для всех
                            Tank t3 = tanksController.getActiveList().get(t);
                            t3.target = null;
                        }
                    }
                    //System.out.println(t2.getHp());
                }

            }

        }
    }

    public boolean isTankSelected(Tank tank) {
        return selectedUnits.contains(tank);
    }

    public void prepareInput() {
        InputProcessor ip = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    selectionStart.set(screenX, 720 - screenY);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) { //если нажата левая кнопка
                    tmp.set(Gdx.input.getX(), 720 - Gdx.input.getY()); //берем координаты

                    if (tmp.x < selectionStart.x) {
                        float t = tmp.x;
                        tmp.x = selectionStart.x;
                        selectionStart.x = t;
                    }
                    if (tmp.y > selectionStart.y) {
                        float t = tmp.y;
                        tmp.y = selectionStart.y;
                        selectionStart.y = t;
                    }

                    selectedUnits.clear(); //выбор областью нескольких
                    if (Math.abs(tmp.x - selectionStart.x) > 20 & Math.abs(tmp.y - selectionStart.y) > 20) {
                        for (int i = 0; i < tanksController.getActiveList().size(); i++) {
                            Tank t = tanksController.getActiveList().get(i);
                            if (t.getOwnerType() == Tank.Owner.PLAYER && t.getPosition().x > selectionStart.x && t.getPosition().x < tmp.x
                                    && t.getPosition().y > tmp.y && t.getPosition().y < selectionStart.y
                            ) {
                                selectedUnits.add(t);
                            }
                        }
                    } else { //клик на одного
                        for (int i = 0; i < tanksController.getActiveList().size(); i++) {
                            Tank t = tanksController.getActiveList().get(i);
                            if (t.getPosition().dst(tmp) < 30.0f) {
                                selectedUnits.add(t);
                            }
                        }
                    }
                }
                return true;
            }
        };
        Gdx.input.setInputProcessor(ip);
    }
}
