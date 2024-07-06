/**
 * @file CreditScreen.java
 * @brief クレジットスクリーンのクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.Pause;
import info.u6182.mygdx.game.myactiongame.ScreenElement;
import info.u6182.mygdx.game.myactiongame.animation.credit.CreditAnimation;

import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class CreditScreen
 * @brief クレジットスクリーンのクラス
 */
public class CreditScreen extends BaseScreen {

    //! ポーズ
    private Pause pause;

    //! アニメーション
    private CreditAnimation animation;

    //! アナザーストーリーフラグ
    private boolean bAnotehrStory;

    //! 分岐クリアフラグ
    private boolean bBranchClear;

    //! 遷移スクリーン番号
    private int screenNo;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     * @param screenNo スクリーン番号
     */
    public CreditScreen(CustomSpriteBatch spriteBatch, int screenNo){

        super(spriteBatch);

        this.screenNo = screenNo;

        this.bAnotehrStory = GameElement.getGameElement().isAnotherStory();

        this.bBranchClear = GameElement.getGameElement().getStoryClear() == GameElement.BRANCHSTORY_CLEAR ? true : false;

        this.animation = new CreditAnimation(this.bAnotehrStory, this.bBranchClear);

    }

    /**
     * @brief スクリーンの表示処理を行う
     */
    @Override
    public void show(){

        //アセットの読み込み
        assetLoad();

        //ワールドカメラの設定
        setCamera(624f, 351f);

        this.pause = new Pause(this.spriteBatch);

        //クレジットの読み込み
        this.animation.creditLoad();

        if(this.bAnotehrStory){

            String path = this.bBranchClear ? "Credit/BranchEndCreditStage.txt" : "Credit/EndCreditStage.txt";

            this.animation.load(path);

            this.animation.initialize(this.spriteBatch, 0);

        }else{

            this.animation.animation(this.spriteBatch);

        }

    }

    /**
     * @brief 更新処理を行う
     */
    public void update(){

        if(this.screenNo == ScreenElement.SCREEN_MENU){

            //バックボタンでメニューセレクトスクリーンへ遷移
            if(Gdx.input.isKeyPressed(Input.Keys.BACK)){

                ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_MENU);

            }

            if(this.pause.update()){

                return;

            }

        }

        //アニメーションの更新
        this.animation.animation(this.spriteBatch);

        //アニメーションが終了した場合
        if(this.animation.isEnd()){

            //ゲームクリアした場合ゲームクリアシーンへ遷移
            if(GameElement.getGameElement().isClearItem() && !this.bAnotehrStory){

                ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_GAMECLEAR);

            //メニューから選択した場合メニューへ遷移
            }else{

                ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_MENU);

            }

        }

    }

    /**
     * @brief 描画処理を行う
     * @param deltaTime 経過時間
     */
    @Override
    public void render(float deltaTime){

        //更新
        update();

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        //アニメーションの描画
        this.animation.render(this.spriteBatch);

        this.spriteBatch.end();

        //ポーズの描画
        if(this.screenNo == ScreenElement.SCREEN_MENU){

            this.pause.render();

        }

    }

    /**
     * @brief 解放処理を行う
     */
    @Override
    public void release(){

        if(this.bAnotehrStory){

            GameElement.getGameElement().initialize();

        }

        //テクスチャの解放
        TextureManager.getTextureManager().release();

        //サウンドの解放
        SoundManager.getSoundManager().release();

    }

    /**
     * @brief スクリーンが隠れたときの処理を行う
     */
    @Override
    public void hide(){

        //インスタンスの解放
        release();

    }

    /**
     * @brief ポーズ処理を行う
     */
    @Override
    public void pause(){

        //全てのサウンドの一時停止
        SoundManager.getSoundManager().allPause();

    }

    /**
     * @brief 再開処理を行う
     */
    @Override
    public void resume(){

        //全てのサウンドの再開
        SoundManager.getSoundManager().allResume();

    }

}
