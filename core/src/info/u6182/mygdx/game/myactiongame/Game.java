/**
 * @file Game.java
 * @brief ゲームの基本的な管理を行うクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import info.u6182.mygdx.game.myactiongame.screen.BaseScreen;

import info.u6182.api.fontmanager.FontManager;
import info.u6182.api.screeneffectmanager.ScreenEffectManager;

/**
 * @class Game
 * @brief ゲームの基本的な管理を行う抽象クラス
 */
public abstract class Game implements ApplicationListener {

    //! 現在の画面
    protected BaseScreen screen;

    /**
     * @brief 画面サイズの変更を行う
     * @param width 幅
     * @param height 高さ
     */
    @Override
    public void resize(int width, int height){

        if(this.screen != null){

            this.screen.resize(width, height);

        }

    }

    /**
     * @brief 画面の描画を行う
     */
    @Override
    public void render(){

        if(this.screen != null){

            this.screen.render(Gdx.graphics.getDeltaTime());

        }

    }

    /**
     * @brief ゲームの一時停止処理を行う
     */
    @Override
    public void pause(){

        if(this.screen != null){

            this.screen.pause();

        }

        //this.screen.assetRelease();

    }

    /**
     * @brief ゲームの再開処理を行う
     */
    @Override
    public void resume(){

        if(this.screen != null){

            this.screen.resume();

        }

        this.screen.assetLoad();

        FontManager.getFontmanager().load();

        ScreenEffectManager.getScreenEffectManager().generateRenderer();

    }

    /**
     * @brief ゲームの終了処理を行う
     */
    @Override
    public void dispose(){

        if(this.screen != null){

            this.screen.hide();

        }

    }

    /**
     * @brief 現在の画面の設定を行う
     * @param screen 新しい画面
     */
    public void setScreen(BaseScreen screen){

        if(this.screen != null){

            this.screen.hide();

        }

        this.screen = screen;

        if(this.screen != null){

            this.screen.show();

            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        }

    }

    /**
     * @brief 現在の画面の取得を行う
     * @return 現在の画面
     */
    public BaseScreen getScreen(){

        return this.screen;

    }

}
