/**
 * @file GameBreakScreen.java
 * @brief ゲーム中断画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.fontmanager.FontManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class GameBreakScreen
 * @brief ゲーム中断画面のクラス
 */
public class GameBreakScreen extends BaseScreen {

    //! 画面の表示時間
    private final float GAMEBREAK_INTERVALTIME = 20f;

    //! 画面の表示時間
    private final float GAMEBREAK_INTERVAL = 0.2f;

    //! プレイヤーのX座標基準値
    private final float PLAYER_OFFSETX = 50f;

    //! プレイヤーのY座標基準値
    private final float PLAYER_OFFSETY = 200f;

    //! ゲーム読み込みスレッド
    private GameLoadThread gameLoadThread;

    //! ゲームスクリーン
    private GameScreen gameScreen;

    //! アニメーション
    private AnimeMotion anime;

    //! プレイヤーの座標
    private Vector2 playerPos;

    //! プレイヤーのサイズ
    private Vector2 playerSize;

    //! プレイヤーの残機
    private int playerLife;

    //! 画面の表示時間
    private float showInterval;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public GameBreakScreen(CustomSpriteBatch spriteBatch){

        super(spriteBatch);

        //アナザーストーリーフラグの初期化
        this.bAnotherStory = GameElement.getGameElement().isAnotherStory();

        //プレイヤーの残機の初期化
        this.playerLife = GameElement.getGameElement().getLife();

        //画面の表示時間の初期化
        this.showInterval = GAMEBREAK_INTERVALTIME;

        this.gameLoadThread = new GameLoadThread(this.spriteBatch);

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

        initialize();

        //プレイヤーの座標の初期化
        this.playerPos = new Vector2(this.camera.viewportWidth * 0.5f - PLAYER_OFFSETX, PLAYER_OFFSETY);

        FontManager.getFontmanager().setSize(18);

        //スレッド開始
        this.gameLoadThread.start();

    }

    /**
     * @brief 初期化処理を行う
     */
    private void initialize(){

        if(this.bAnotherStory){

            //プレイヤーサイズの初期化
            this.playerSize = new Vector2(64f, 64f);

            Texture texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKING);

            AnimeData[] animeData = {new AnimeData(texture,"待機", this.playerSize,true, new int[][]{{0,0},{1,0},{2,0}}, 0,0,0.05f)};

            //アニメーションの初期化
            this.anime = new AnimeMotion(animeData, this.spriteBatch);

        }else{

            //プレイヤーサイズの初期化
            this.playerSize = new Vector2(60f, 64f);

            Texture texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PLAYER);

            AnimeData[] animeData = {new AnimeData(texture,"待機", this.playerSize,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,0.05f)};

            //アニメーションの初期化
            this.anime = new AnimeMotion(animeData, this.spriteBatch);

        }

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime 経過時間
     */
    public void update(float deltaTime){

        //表示時間の更新
        this.showInterval -= GAMEBREAK_INTERVAL;

        //表示時間が0以下になった場合ゲームスクリーンへ遷移
        if(this.showInterval <= 0 && this.gameLoadThread.isThreadEnd()){

            ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_GAME);

            this.gameScreen = this.gameLoadThread.getGameScreen();

        }

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 描画処理を行う
     * @param deltaTime 経過時間
     */
    @Override
    public void render(float deltaTime){

        //更新
        update(deltaTime);

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        Texture backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_GAMEBREAKBACKGROUND);

        //背景の描画
        this.spriteBatch.draw(backGround, 0, 0);

        //プレイヤーの描画
        this.anime.render(this.playerPos.x, this.playerPos.y);

        //プレイヤーの残機の描画
        FontManager.getFontmanager().render("× " + this.playerLife, Color.WHITE, this.playerPos.x + this.playerSize.x, this.playerPos.y + this.playerSize.y * 0.5f);

        this.spriteBatch.end();

    }

    /**
     * @brief ゲームスクリーンの取得を行う
     * @return ゲームスクリーン
     */
    public GameScreen getGameScreen(){

        return this.gameScreen;

    }

    /**
     * @brief 解放処理を行う
     */
    @Override
    public void release(){
        ;
        //テクスチャの解放
        //TextureManager.getTextureManager().release();

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

    }

    /**
     * @brief 再開処理を行う
     */
    @Override
    public void resume(){

    }

}

/**
 * @class GameLoadThread
 * @brief ゲーム読み込みスレッドのクラス
 */
class GameLoadThread extends Thread {

    //! ゲームスクリーン
    private GameScreen gameScreen;

    //! スレッドフラグ
    private boolean bThreadEnd;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public GameLoadThread(CustomSpriteBatch spriteBatch){

        this.gameScreen = new GameScreen(spriteBatch);

        this.bThreadEnd = false;

    }

    /**
     * @brief スレッド実行
     */
    @Override
    public void run(){

        this.gameScreen.show();

        this.bThreadEnd = true;

    }

    /**
     * @brief ゲームスクリーンの取得を行う
     * @return ゲームスクリーン
     */
    public GameScreen getGameScreen(){

        return this.gameScreen;

    }

    /**
     * @brief スレッド終了フラグの取得を行う
     * @return スレッド終了フラグ
     */
    public boolean isThreadEnd(){

        return this.bThreadEnd;

    }

}
