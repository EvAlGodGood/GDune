package com.dune.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dune.game.core.Assets;
import com.dune.game.core.gui.GuiPlayerInfo;


public class MenuScreen extends AbstractScreen {
    private Stage stage2;

    public Stage getStage2() {
        return stage2;
    }

    public MenuScreen(SpriteBatch batch) {
        super(batch);
        //createGuiMenu();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(0, 0.7f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //getStage2().draw();
    }


    public void update(float dt) {

       if (Gdx.input.justTouched()) {
            ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
       }
//       stage2.act(dt);

    }

    @Override
    public void dispose() {
    }

//    public void createGuiMenu() {
//        stage2 = new Stage(ScreenManager.getInstance().getViewport(), ScreenManager.getInstance().getBatch());
//        Gdx.input.setInputProcessor(new InputMultiplexer(stage2));
//        Skin skin2 = new Skin();
//        skin2.addRegions(Assets.getInstance().getAtlas());
//        BitmapFont font14 = Assets.getInstance().getAssetManager().get("fonts/font14.ttf");
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
//                skin2.getDrawable("smButton"), null, null, font14);
//        final TextButton startBtn = new TextButton("Start New Game", textButtonStyle);
//        startBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.GAME);
//            }
//        });
//
//        final TextButton exitBtn = new TextButton("Exit Game", textButtonStyle);
//        exitBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });
//
////        Group menuGroup = new Group();
////        startBtn.setPosition(0, 0);
////        exitBtn.setPosition(130, 0);
////        menuGroup.addActor(startBtn);
////        menuGroup.addActor(exitBtn);
////        menuGroup.setPosition(300, 300);
////
////        Label.LabelStyle labelStyle = new Label.LabelStyle(font14, Color.WHITE);
////        skin.add("simpleLabel", labelStyle);
////
////        guiPlayerInfo = new GuiPlayerInfo(playerLogic, skin);
////        guiPlayerInfo.setPosition(0, 700);
////        stage.addActor(guiPlayerInfo);
//        stage2.addActor(startBtn);
//        stage2.addActor(exitBtn);
//        skin2.dispose();
//    }
}