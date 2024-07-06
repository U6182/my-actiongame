/**
 * @file GameScreen.java
 * @brief ゲーム画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.PerformanceCounter;

import info.u6182.api.camera.Camera;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.Load;
import info.u6182.mygdx.game.myactiongame.Pause;
import info.u6182.mygdx.game.myactiongame.ScreenElement;
import info.u6182.mygdx.game.myactiongame.call.EnemyCall;
import info.u6182.mygdx.game.myactiongame.controller.Controller;
import info.u6182.mygdx.game.myactiongame.stage.Stage;
import info.u6182.mygdx.game.myactiongame.substance.anime.Anime;
import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;
import info.u6182.mygdx.game.myactiongame.substance.anime.obsession.Obsession;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.AnotherPlayer;
import info.u6182.mygdx.game.myactiongame.substance.anime.player.Player;
import info.u6182.mygdx.game.myactiongame.substance.fireball.FireBall;
import info.u6182.mygdx.game.myactiongame.substance.item.Item;
import info.u6182.mygdx.game.myactiongame.Save;
import info.u6182.mygdx.game.myactiongame.ui.UI;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import info.u6182.api.Utiility;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.fontmanager.FontManager;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class GameScreen
 * @brief ゲーム画面のクラス
 */
public class GameScreen extends BaseScreen {

    PerformanceCounter performanceCounter = new PerformanceCounter("");

    //! UIカメラ
    private Camera uiCamera;

    //! ステージ
    private Stage stage;

    //! プレイヤー
    private Player player;

    //! 強迫観念
    private Obsession obsession;

    //! 敵
    private List<Enemy> enemyList = new ArrayList<>();

    //! アイテム
    private List<Item> itemList = new ArrayList<>();

    //! コントローラ
    private Controller controller;

    //! ポーズ
    private Pause pause;

    //! UI
    private UI ui;

    //! 埋まり値
    private Vector2 buried;

    //! 落下埋まり値
    private Vector2 fallBuried;

    //! プレイヤー反転フラグ
    private boolean bPlayerReverse;

    //! カメラ反転フラグ
    private boolean bCameraReverse;

    private boolean bCameraReset;

    //! 現在のステージ番号
    private int stageNo;

    //! クリアしたステージの合計スコア
    private int sumFameScore;

    //! ハイ名声スコア
    private int highFameScore;

    //! カメラX座標移動のオフセット
    private final float CAMERAMOVE_OFFSETX = 200f;

    //! カメラY座標移動のオフセット
    private final float CAMERAMOVE_OFFSETY = 100f;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public GameScreen(CustomSpriteBatch spriteBatch){

        super(spriteBatch);

        //埋まり値の初期化
        this.buried = new Vector2();

        //落下埋まり値の初期化
        this.fallBuried = new Vector2();

        //アナザーストーリーフラグの初期化
        this.bAnotherStory = GameElement.getGameElement().isAnotherStory();

        //カメラ反転フラグの初期化
        this.bCameraReverse = false;

        this.bCameraReset = false;

        //現在のステージ番号の初期化
        this.stageNo = GameElement.getGameElement().getSelectStageNo();

        SecretKey key = GameElement.getGameElement().getKey();

        IvParameterSpec iv = GameElement.getGameElement().getIv();

        //ハイ名声スコアの初期化
        this.highFameScore = Load.getLoad().highFameScoreLoad(key, iv);

    }

    /**
     * @brief スクリーンが表示されたときの処理を行う
     */
    @Override
    public void show(){

        if(this.bAnotherStory && this.stageNo == GameElement.STAGE3){

            //ワールドカメラの設定
            setCamera(1024f, 768f);

        }else{

            //ワールドカメラの設定
            setCamera(624f, 351f);

        }

        this.uiCamera = new Camera();

        //カメラを右上に移動　1:Y軸の反転 2.3:カメラの幅と高さ
        this.uiCamera.setToOrtho(false, 480f, 320f);

        //コントローラの生成
        this.controller = new Controller(this.bAnotherStory, this.uiCamera.viewportWidth, this.uiCamera.viewportHeight);

        //ポーズの初期化
        this.pause = new Pause(this.spriteBatch);

        //生成と読み込み
        load();

        //初期化
        initialize();

    }

    /**
     * @brief ロード処理を行う
     */
    public void load(){

        //ステージの生成
        this.stage = new Stage();

        this.stage.load();

        //プレイヤーの生成
        if(this.bAnotherStory){

            this.player = new AnotherPlayer(this.stage.getPlayerPos());

        }else{

            int fameScore = Load.getLoad().gameFameScoreLoad(this.stageNo);

            this.player = new Player(this.stage.getPlayerPos(), fameScore);

            this.obsession = new Obsession(this.spriteBatch, this.stage.getPlayerPos());

        }

        this.ui = new UI();

        this.camera.position.y = this.player.getPos().y;

    }

    /**
     * @brief 初期化処理を行う
     */
    public void initialize(){

        //プレイヤーの初期化
        this.player.initialize(this.spriteBatch);

        //ステージと敵とアイテムの初期化
        this.stage.initialize(this.enemyList, this.itemList, this.spriteBatch);

    }

    /**
     * @brief 画面サイズが変わった場合の処理を行う
     */
    @Override
    public void screenResize(){

        Vector2 cameraPos = this.stage.getCameraPos();

        //カメラをビューポートの中心に設定
        this.camera.position.set(cameraPos.x,cameraPos.y,0);

        Vector2 scroll = GameElement.getGameElement().getScroll();

        scroll.x = this.player.getPos().x - CAMERAMOVE_OFFSETX < 0 ? 0f : this.player.getPos().x - CAMERAMOVE_OFFSETX;

        if(this.stageNo == GameElement.STAGE4){

            scroll.y = this.stage.getStageHeight() - this.camera.viewportHeight;

        }

        FontManager.getFontmanager().setSize(12);

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime 経過時間
     */
    public void update(float deltaTime){

        if(!this.player.isDead() && !this.player.isDamage() && this.pause.update()){

            return;

        }

        if(this.stage.isCameraReset() && !this.bCameraReset){

            //ワールドカメラの設定
            //setCamera(624f, 351f);

            //画面サイズが変わった場合再設定
            //this.viewport.update(624, 351);

            //カメラをビューポートの中心に設定
            //this.camera.position.set(this.camera.viewportWidth / 2,this.camera.viewportHeight / 2,0);

            //ビューポートサイズを伝える
            //GameElement.getGameElement().setViewPortSize(this.camera.viewportWidth, this.camera.viewportHeight);

            //this.bCameraReset = true;

        }

        if(!this.player.isDead()){

            this.stage.update(this.player.getEnemyCall() == null ? false : this.player.getEnemyCall().isChange());

        }

        this.controller.update(this.uiCamera, this.touchPos);

        //カメラの位置設定
        setCameraPos();

        //エフェクトの更新
        EffectManager.getEffectManager().update(deltaTime);

        //プレイヤーの更新
        playerUpdate(deltaTime);

        //強迫観念の更新
        obsessionUpdate(deltaTime);

        //敵の出現管理の更新
        enemyAppear();

        //敵の更新
        enemyUpdate(deltaTime);

        //アイテムの出現管理の更新
        itemAppear();

        //アイテムの更新
        itemUpdate(deltaTime);

        //ステージとの衝突判定の更新
        stageUpdate();

        //スクリーン遷移の更新
        screenChange();

        this.controller.resset();

    }

    /**
     * @brief プレイヤーの更新処理を行う
     * @param deltaTime 経過時間
     */
    private void playerUpdate(float deltaTime){

        //プレイヤーの更新
        this.player.update(deltaTime);

        this.buried.set(0f, 0f);

        //プレイヤーとステージの衝突判定
        if(this.stage.collision(this.player.getRect(), this.buried) && !this.player.isDamage()){

            this.stage.collisionItemStage(this.player);

            this.player.collisionStage(this.buried);

        }

        this.buried.set(0f, 0f);

        //召喚した敵とステージの衝突判定
        if(this.bAnotherStory){

            AnotherPlayer anotherPlayer = null;

            if(this.player instanceof AnotherPlayer){

                anotherPlayer = (AnotherPlayer)this.player;

            }

            EnemyCall enemyCall = anotherPlayer.getEnemyCall();

            int callCount = enemyCall.getCallCount();

            for(int i = 0;i < callCount;i++){

                if(enemyCall.getEnemy(i).isDead()){

                    continue;

                }

                this.buried.set(0f, 0f);

                if(this.stage.collision(enemyCall.getEnemy(i).getRect(), this.buried)){

                    if(enemyCall.getEnemy(i).isFirstFrame()){

                        anotherPlayer.addCallPower(enemyCall.getEnemy(i).getCallCost());

                        enemyCall.release(i);

                        continue;

                    }

                    enemyCall.collisionStage(this.buried, i);

                }

                enemyCall.getEnemy(i).setFirstFrame(false);

            }

        }else{

            FireBall fireBall = this.player.getFireBallCall().getFireBall();

            if(fireBall.isShow() && this.stage.collision(fireBall.getRect(), this.buried)){

                fireBall.collisionStage(this.buried);

            }

        }

    }

    /**
     * @brief 強迫観念の更新処理を行う
     * @param deltaTime 経過時間
     */
    private void obsessionUpdate(float deltaTime){

        boolean bObsession = this.player.getGuilt() > 0 ? true : false;

        if(this.bAnotherStory || !bObsession){

            return;

        }

        //強迫観念の更新
        this.obsession.update(this.player, this.stage.getChipSize(), deltaTime);

        this.buried.set(0f, 0f);

        this.fallBuried.set(0f, 0f);

        //強迫観念とステージの衝突判定
        if(this.stage.collision(this.obsession.getRect(), this.buried)){

            if(!this.stage.collision(this.obsession.fallRect(), this.fallBuried)){

                this.obsession.setFall(this.obsession.OBSESSION_FALL);

            }else{

                this.obsession.setFall(this.obsession.OBSESSION_FALLNOT);

            }

            this.obsession.collisionStage(this.buried);

        }

        //強迫観念とプレイヤーの衝突判定
        if(this.player.collisionObsession(this.obsession.getRect(), this.obsession.isShow())){

            this.obsession.setObsession();

        }

    }

    /**
     * @brief 敵の出現管理を行う
     */
    private void enemyAppear(){

        //敵が画面内に現れた場合表示
        for(Enemy enemy : this.enemyList){

            if(enemy.isDead() || enemy.isShow()){

                continue;

            }

            Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

            Vector2 pos = Utiility.getScrollSubPos(enemy.getPos(), GameElement.getGameElement().getScroll());

            if((pos.x + enemy.getSize().x > 0 && pos.x < viewSize.x) && (pos.y + enemy.getSize().y > 0 && pos.y < viewSize.y)){

                enemy.setShow(true);

            }

        }

    }

    /**
     * @brief アイテムの出現管理を行う
     */
    private void itemAppear(){

        //アイテムが画面内に現れた場合表示
        for(Item item : this.itemList){

            if(item.getType() == GameElement.ITEM_CLEARGOAL || item.getType() == GameElement.ITEM_ASSASSINCLEARGOAL || item.isShow() || item.isGetItem()){

                continue;

            }

            Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

            Vector2 pos = Utiility.getScrollSubPos(item.getPos(), GameElement.getGameElement().getScroll());

            if((pos.x + item.getSize().x > 0 && pos.x < viewSize.x) && (pos.y + item.getSize().y > 0 && pos.y < viewSize.y)){

                item.setShow(true);

            }

        }

    }

    /**
     * @brief 敵の更新処理を行う
     * @param deltaTime 経過時間
     */
    private void enemyUpdate(float deltaTime){

        for(Enemy enemy : this.enemyList){

            if(!enemy.isShow() || this.player.isGoal()){

                continue;

            }

            //敵の更新
            enemy.update(this.player, deltaTime);

            if(enemy.isDead()){

                continue;

            }

            this.buried.set(0f, 0f);

            this.fallBuried.set(0f, 0f);

            //敵とステージの衝突判定
            if(this.stage.collision(enemy.getRect(), this.buried)){


                if(!this.stage.collision(enemy.fallRect(), this.fallBuried)){

                    enemy.setFall(true);

                }else{

                    enemy.setFall(false);

                }

                enemy.collisionStage(this.buried);

            }

        }

    }

    /**
     * @brief アイテムの更新処理を行う
     * @param deltaTime 経過時間
     */
    private void itemUpdate(float deltaTime){

        //アイテムの更新
        for(Item item : this.itemList){

            item.update(deltaTime);

        }

    }

    /**
     * @brief ステージの更新処理を行う
     */
    private void stageUpdate(){

        //プレイヤーと敵の衝突判定
        for(Enemy enemy : this.enemyList){

            this.player.collisionEnemy(enemy);

        }

        //プレイヤーとアイテムの衝突判定
        for(Item item : this.itemList){

            this.player.collisionItem(item);

        }

    }

    /**
     * @brief スクリーン遷移処理を行う
     */
    private void screenChange(){

        //ゲーム終了フラグ
        boolean bGameEnd = false;

        //ストーリーのクリア番号
        int storyClear = GameElement.getGameElement().getStoryClear();

        //プレイヤーが死亡して残機が存在する場合
        if(this.player.isDead() && this.player.getLife() > 0){

            //ステージBGMを停止し死亡BGMを再生
            if(SoundManager.getSoundManager().isBGMPlaying(this.stage.getBgmPath())){

                SoundManager.getSoundManager().allStop();

                SoundManager.getSoundManager().bgmPlay(SoundInfo.BGM_DEAD, false);

            }

            //死亡BGMが停止した場合プレイヤーの残機を減らしステージセレクトへ遷移
            if(!SoundManager.getSoundManager().isBGMPlaying(SoundInfo.BGM_DEAD)){

                this.player.decreaseLife();

                GameElement.getGameElement().setLife(this.player.getLife());

                int nextScreen = this.bAnotherStory ? ScreenElement.SCREEN_ANOTHERSTORY : ScreenElement.SCREEN_STORY;

                ScreenElement.getElement().setNextScreen(nextScreen);

            }

        //プレイヤーの残機が存在せず死亡した場合
        }else if(this.player.isDead()){

            SoundManager.getSoundManager().allStop();

            ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_GAMEOVER);

            bGameEnd = true;

        }else if(this.player.isGoalEnd() || storyClear > 0){

            if(storyClear == GameElement.STORY_CLEAR && !GameElement.getGameElement().isClearItem()){

                return;

            }

            SoundManager.getSoundManager().allStop();

            //クリアステージの設定
            GameElement.getGameElement().setClearStageNo(this.stageNo + 1);

            //クリアしたステージのスコアを保存
            Save.getSave().stageFameScoreSave(this.stageNo, this.player.getFameScore());

            if(storyClear > 0){

                ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_CREDIT);

            }else{

                int nextScreen = this.bAnotherStory ? ScreenElement.SCREEN_ANOTHERSTORY : ScreenElement.SCREEN_STORY;

                ScreenElement.getElement().setNextScreen(nextScreen);

            }

        }

        if(bGameEnd && !this.bAnotherStory){

            SecretKey key = GameElement.getGameElement().getKey();

            IvParameterSpec iv = GameElement.getGameElement().getIv();

            //合計名声スコアを読み込む
            this.sumFameScore = Load.getLoad().sumFameScoreLoad(key, iv);

            //合計名声スコアがハイ名声スコアを上回っている場合ハイスコアに保存
            if(this.sumFameScore > this.highFameScore){

                Save.getSave().highFameScoreSave(key, iv, this.sumFameScore);

                GameElement.getGameElement().setHighClearStageNo(this.stageNo);

            }

            GameElement.getGameElement().getSettings().bNotPlay = false;

        }

    }

    /**
     * @brief カメラの位置設定を行う
     */
    private void setCameraPos(){

        Vector2 scroll = GameElement.getGameElement().getScroll();

        GameElement.getGameElement().getCameraPos().set(this.camera.position.x, this.camera.position.y);

        Anime anime = this.player;

        if(anime instanceof AnotherPlayer){

            EnemyCall enemyCall = ((AnotherPlayer) anime).getEnemyCall();

            enemyCall.getCameraInfo().reset(this.camera, scroll);

            if(anime.isDamage() && !enemyCall.isChange()){

                return;

            }

            if(anime.isDamage() && enemyCall.isChange()){

                enemyCall.setChange(false);

                enemyCall.getCameraInfo().setReset(true);

                enemyCall.getCameraInfo().reset(this.camera, scroll);

            }

            if(enemyCall.isChange() && enemyCall.getEnemy(0).isDead()){

                return;

            }

            if(enemyCall.isChange()){

                anime = enemyCall.getEnemy(0);

            }

        }

        if(this.stage.getBossStage() == this.stage.BOSS_STAGE_NOT) {// && this.stage.getBossStage() != this.stage.BOSS_STAGE_HELLISHESTKING){

            if(anime.getMove().x != 0 || this.bCameraReverse || anime.isDead() || anime.isDamage()){

                setCameraPosX(anime, scroll);

            }

            if(anime.getMove().y != 0 && (!anime.isDead() && !anime.isDamage())){

                setCameraPosY(anime, scroll);

            }

        }

        this.camera.update();

        this.uiCamera.update();

    }

    /**
     * @brief カメラのX軸位置設定を行う
     * @param anime アニメーション
     * @param scroll スクロール
     */
    public void setCameraPosX(Anime anime, Vector2 scroll){

        cameraPosReverse(anime, scroll);

        if(anime.isReverse()){

            //プレイヤーの追従
            if(anime.getPos().x + anime.getSize().x - scroll.x < this.camera.viewportWidth - CAMERAMOVE_OFFSETX && this.camera.getPosLeft() > 0){

                scroll.x += anime.getMove().x;

                this.camera.position.x += anime.getMove().x;

                if(this.camera.getPosLeft() < 0){

                    scroll.x = 0f;

                    this.camera.position.x = this.camera.getViewCenterX();

                }

            }

        }else{

            //プレイヤーの追従
            if(anime.getPos().x > CAMERAMOVE_OFFSETX && this.camera.getPosRight() < this.stage.getStageWidth()){

                scroll.x += anime.getMove().x;

                this.camera.position.x += anime.getMove().x;

                if(this.camera.getPosRight() > this.stage.getStageWidth()){

                    scroll.x = this.stage.getStageWidth() - this.camera.viewportWidth;

                    this.camera.position.x = this.stage.getStageWidth() - this.camera.getViewCenterX();

                }

            }

        }

    }

    /**
     * @brief カメラのY軸位置設定を行う
     * @param anime アニメーション
     * @param scroll スクロール
     */
    public void setCameraPosY(Anime anime, Vector2 scroll){

        if(anime.getMove().y > 0){

            if(anime.getPos().y - scroll.y > this.camera.viewportHeight - CAMERAMOVE_OFFSETY && this.camera.getPosTop() < this.stage.getStageHeight()){

                scroll.y += anime.getMove().y;

                this.camera.position.y += anime.getMove().y;

                if(this.camera.getPosTop() > this.stage.getStageHeight()){

                    scroll.y = this.stage.getStageHeight() - this.camera.viewportHeight;

                    this.camera.position.y = this.stage.getStageHeight() - this.camera.getViewCenterY();

                }

            }

        }else if(anime.getMove().y < 0){

            if(anime.getPos().y - scroll.y < 50 && this.camera.getPosButtom() > 0){

                scroll.y += anime.getMove().y;

                this.camera.position.y += anime.getMove().y;

                if(this.camera.getPosButtom() < 0){

                    scroll.y = 0f;

                    this.camera.position.y = this.camera.getViewCenterY();

                }

            }

        }

    }

    /**
     * @brief カメラの位置反転設定を行う
     * @param anime アニメーション
     * @param scroll スクロール
     */
    public void cameraPosReverse(Anime anime, Vector2 scroll){

        if((!this.bPlayerReverse && anime.isReverse()) || (this.bPlayerReverse && !anime.isReverse())){

            this.camera.reveseReset();

            this.bCameraReverse = true;

        }

        this.bPlayerReverse = anime.isReverse();

        float leftCameraPos = (anime.getPos().x + anime.getSize().x) - (camera.getViewCenterX() - CAMERAMOVE_OFFSETX);
        float rightCameraPos = anime.getPos().x + (camera.getViewCenterX() - CAMERAMOVE_OFFSETX);

        if(this.bCameraReverse){

            if(this.bPlayerReverse && this.camera.getPosLeft() > 0){

                this.camera.posReverse(this.bPlayerReverse, this.stage.getStageWidth());

                if(camera.position.x < leftCameraPos){

                    this.bCameraReverse = false;

                }

            }else if(!this.bPlayerReverse && this.camera.getPosRight() < this.stage.getStageWidth()){

                this.camera.posReverse(this.bPlayerReverse, this.stage.getStageWidth());

                if(camera.position.x > rightCameraPos){

                    this.bCameraReverse = false;

                }

            }else{

                this.camera.reveseReset();

                this.bCameraReverse = false;

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
        update(deltaTime);

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        //ステージの描画
        this.stage.render(this.spriteBatch, this.camera);

        //エフェクトの描画
        EffectManager.getEffectManager().render();

        //プレイヤーの描画
        this.player.render(this.spriteBatch);

        //強迫観念の描画
        if(!this.bAnotherStory){

            this.obsession.render();

        }

        //敵の描画
        for(Enemy enemy : this.enemyList){

            enemy.render();

        }

        //アイテムの描画
        for(Item item : this.itemList){

            item.render(this.spriteBatch);

        }

        this.spriteBatch.end();

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.uiCamera.combined);

        boolean bPlayerChange = false;

        if(this.player instanceof AnotherPlayer){

            AnotherPlayer anotherPlayer = (AnotherPlayer)this.player;

            bPlayerChange = anotherPlayer.getEnemyCall().isChange();

        }

        //コントローラの描画
        this.controller.render(this.spriteBatch, bPlayerChange);

        //UIの描画
        this.ui.render(this.player, this.spriteBatch, this.uiCamera);

        this.spriteBatch.end();

        //ポーズメニューの描画
        this.pause.render();

        //renderDebug();

    }

    /**
     * @brief デバッグ描画処理を行う
     */
    public void renderDebug(){

        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.setProjectionMatrix(this.camera.combined);


        for(Enemy enemy : enemyList){

            CustomRectangle rect = enemy.getRect();

            rect.set(1696,96,32,32);

            renderer.renderRect(rect);

            if(enemy.getType() != GameElement.ENEMY_PUMPKIN){

                continue;

            }


        }

        renderer.end();

    }

    /**
     * @brief 解放処理を行う
     */
    @Override
    public void release(){

        //テクスチャの解放
        TextureManager.getTextureManager().release();

        //エフェクトの解放
        EffectManager.getEffectManager().release();

        //サウンドの解放
        SoundManager.getSoundManager().release();

        GameElement.getGameElement().getScroll().set(0f, 0f);

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
