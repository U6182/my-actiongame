/**
 * @file FameScoreScreen.java
 * @brief 名声スコア画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.Load;
import info.u6182.mygdx.game.myactiongame.Pause;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.fontmanager.FontManager;
import info.u6182.api.screeneffectmanager.ScreenEffectManager;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class FameScoreScreen
 * @brief 名声スコア画面のクラス
 */
public class FameScoreScreen extends BaseScreen {

    //! ポーズ
    private Pause pause;

    //! 背景テクスチャパス
    private final String[] BACKGROUND = {TextureInfo.TEXTURE_STAGE1BACKGROUND, TextureInfo.TEXTURE_STAGE2BACKGROUND, TextureInfo.TEXTURE_STAGE3BACKGROUND, TextureInfo.TEXTURE_STAGE4BACKGROUND, TextureInfo.TEXTURE_STAGE5BACKGROUND, TextureInfo.TEXTURE_STAGE6BACKGROUND};

    //! 地位
    private int status;

    //! 過去最高の地位
    private int highStatus;

    //! どこのスクリーンから遷移したか
    private int screenNo;

    //! プレイヤーが最後にクリアしたステージ番号
    private int stageNo;

    //! 名声スコア
    private int fameScore;

    //! ハイ名声スコア
    private int highFameScore;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     * @param screenNo スクリーン番号
     */
    public FameScoreScreen(CustomSpriteBatch spriteBatch, int screenNo){

        super(spriteBatch);

        this.screenNo = screenNo;

        //プレイヤーが最後にクリアしたステージ番号の初期化
        if(this.screenNo == ScreenElement.SCREEN_MENU){

            this.stageNo = GameElement.getGameElement().getHighClearStageNo();

        }else{

            this.stageNo = GameElement.getGameElement().getSelectStageNo();

        }


        SecretKey key = GameElement.getGameElement().getKey();

        IvParameterSpec iv = GameElement.getGameElement().getIv();

        //名声スコアの初期化
        this.fameScore = Load.getLoad().sumFameScoreLoad(key, iv);

        //ハイ名声スコアの初期化
        this.highFameScore = Load.getLoad().highFameScoreLoad(key, iv);

        //地位の初期化
        for(this.status = 0;this.status < GameElement.STATUS_NAME.length - 1 && this.fameScore >= GameElement.STATUS_FAMESCORE[this.status + 1];this.status++){


        }

        //過去最高の地位の初期化
        for(this.highStatus = 0;this.highStatus < GameElement.STATUS_NAME.length - 1 && this.highFameScore >= GameElement.STATUS_FAMESCORE[this.highStatus + 1];this.highStatus++){


        }

    }

    /**
     * @brief スクリーンが表示されたときの処理を行う
     */
    @Override
    public void show(){

        //アセットの読み込み
        assetLoad();

        //ワールドカメラの設定
        setCamera(624f, 351f);

        FontManager.getFontmanager().setSize(18);

        this.pause = new Pause(this.spriteBatch);

    }

    /**
     * @brief 更新処理を行う
     */
    public void update(){

        if(this.screenNo == ScreenElement.SCREEN_MENU){

            this.pause.update();

        }else{

            //遷移エフェクトの更新
            ScreenEffectManager.getScreenEffectManager().rectOrderLined(ScreenElement.SCREEN_MENU);

            //操作の更新
            operation();

        }

        //ワールドカメラの更新
        this.camera.update();

    }

    /**
     * @brief 操作の処理を行う
     */
    private void operation(){

        //画面がタッチされた場合
        if(Gdx.input.isTouched() && !ScreenElement.getElement().isEffect()){

            SoundManager.getSoundManager().play(SoundInfo.SOUND_CLICK);

            //遷移エフェクトの開始
            ScreenElement.getElement().setEffect(true);

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

        Texture backGround = TextureManager.getTextureManager().getTexture(this.BACKGROUND[this.stageNo]);

        //背景の描画
        for(float y = 0;y < this.camera.viewportHeight;y += backGround.getHeight()){

            for(float x = 0;x < this.camera.viewportWidth;x += backGround.getWidth()){

                this.spriteBatch.draw(backGround, x, y);

            }

        }

        if(this.screenNo == ScreenElement.SCREEN_MENU){

            menuStatusRender();

        }else{

            gameStatusRender();

        }

        this.spriteBatch.end();

        if(this.screenNo == ScreenElement.SCREEN_MENU){

            //ポーズの描画
            this.pause.render();

        }else{

            //遷移エフェクトの描画
            ScreenEffectManager.getScreenEffectManager().rectOrderLinedRender(this.camera.combined);

        }

    }

    /**
     * @brief ゲームステータスの描画を行う
     */
    private void gameStatusRender(){

        int fontSize = FontManager.getFontmanager().getSize();

        float offsetX = this.camera.viewportWidth * 0.5f - fontSize * 3;

        float offsetY = this.camera.viewportHeight * 0.5f - ((fontSize * 3) + (50 * 2));

        FontManager.getFontmanager().render("スコア", Color.WHITE, offsetX, offsetY + 200f);

        FontManager.getFontmanager().render("名声　" + this.fameScore, offsetX, offsetY + 250f);

        FontManager.getFontmanager().render("地位　" + GameElement.STATUS_NAME[this.status], offsetX, offsetY + 300f);

        FontManager.getFontmanager().render("ハイスコア", offsetX, offsetY);

        FontManager.getFontmanager().render("名声　" + this.highFameScore, offsetX, offsetY + 50f);

        FontManager.getFontmanager().render("地位　" + GameElement.STATUS_NAME[this.highStatus], offsetX, offsetY + 100f);

    }

    /**
     * @brief メニューステータスの描画を行う
     */
    private void menuStatusRender(){

        int fontSize = FontManager.getFontmanager().getSize();

        float offsetX = this.camera.viewportWidth * 0.5f - fontSize * 3;

        float offsetY = this.camera.viewportHeight * 0.5f - ((fontSize * 3) + (50 * 3));

        FontManager.getFontmanager().render("ハイスコア", offsetX, offsetY + 200f);

        FontManager.getFontmanager().render("名声　" + this.highFameScore, offsetX, offsetY + 250f);

        FontManager.getFontmanager().render("地位　" + GameElement.STATUS_NAME[this.highStatus], offsetX, offsetY + 300f);

    }

    /**
     * @brief 解放処理を行う
     */
    @Override
    public void release(){

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

        GameElement.getGameElement().initialize();

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
