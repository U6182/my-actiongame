package info.u6182.mygdx.game.myactiongame.screen;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

public class SampleScreen extends BaseScreen {

    public SampleScreen(CustomSpriteBatch spriteBatch){

        super(spriteBatch);

    }

    @Override
    public void show(){


    }

    @Override
    public void render(float deltaTime){


    }

    @Override
    public void release(){

        //テクスチャの解放
        TextureManager.getTextureManager().release();

        //サウンドの解放
        SoundManager.getSoundManager().release();

    }

    @Override
    public void hide(){

        //インスタンスの解放
        release();

    }

    @Override
    public void pause(){

        //全てのサウンドの一時停止
        SoundManager.getSoundManager().allPause();

    }

    @Override
    public void resume(){

        //全てのサウンドの再開
        SoundManager.getSoundManager().allResume();

    }

}
