/**
 * @file StageSelectScreen.java
 * @brief ステージ選択画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.input.InputManager;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.Pause;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import java.util.ArrayList;
import java.util.List;

import info.u6182.api.Utiility;
import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.screeneffectmanager.ScreenEffectManager;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class StageSelectScreen
 * @brief ステージ選択画面のクラス
 */
public class StageSelectScreen extends BaseScreen {

    //! 待機アニメーション番号
    public static final int ANIME_WAIT = 0;

    //! 移動アニメーション番号
    public static final int ANIME_MOVE = 1;

    //! アナザーステージ数
    private final int ANOTHERSTAGE_COUNT = 3;

    //! ステージ数
    private final int STAGE_COUNT = 6;

    //! プリミティブ描画
    private ShapeRenderer renderer = new ShapeRenderer();

    //! 各ステージの座標
    private List<Vector2> stagePos = new ArrayList<>();

    //! ステージの数分の各ステージの色
    private List<Color> stageColor = new ArrayList<>();

    //! 遷移するステージのテキスト名
    private List<String> stagePath = new ArrayList<>();

    //! ステージのタイトル
    private List<String> stageName = new ArrayList<>();

    //! ステージのタイトル座標
    private List<Vector2> stageNamePos = new ArrayList<>();

    //! アニメーション
    private AnimeMotion anime;

    private Pause pause;

    //! プレイヤーの座標
    private Vector2 playerPos;

    //! 次のステージへのプレイヤーの移動量
    private Vector2 move = new Vector2();

    //! プレイヤーのサイズ
    private Vector2 size;

    //! ステージの円
    private Circle stageCircle;

    //! プレイヤーの円
    private Circle playerCircle;

    //! ステージのタッチ円の半径
    private final float[] TOUCH_CIRCLERADIUS = {50f, 50f, 50f, 50f, 50f, 100f};

    //! 移動操作フラグ
    private boolean bOperation = false;

    //! 反転フラグ
    private boolean bReverse = false;

    //! ステージ数
    private int stageCount;

    //! ステージの選択番号
    private int stageNo;

    //! クリアしたステージ番号
    private int clearNo;

    //! 現在のクリアしたステージ番号
    private int nowClearNo = 0;

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public StageSelectScreen(CustomSpriteBatch spriteBatch){

        super(spriteBatch);

        //ステージの円
        this.stageCircle = new Circle();

        //プレイヤーの円
        this.playerCircle = new Circle();

        //アナザーストーリーフラグの初期化
        this.bAnotherStory = GameElement.getGameElement().isAnotherStory();

        //ステージ数の初期化
        this.stageCount = this.bAnotherStory ? ANOTHERSTAGE_COUNT : STAGE_COUNT;

        //ステージの選択番号の初期化
        this.stageNo = GameElement.getGameElement().getSelectStageNo();

        //クリアしたステージ番号の初期化
        this.clearNo = GameElement.getGameElement().getClearStageNo();

    }

    /**
     * @brief スクリーンが表示されたときの処理を行う
     */
    @Override
    public void show(){

        //アセットの読み込み
        assetLoad();

        //ワールドカメラの設定
        setCamera(1024f, 768f);

        //読み込み
        load();

        //初期化
        initialize();

        this.pause = new Pause(this.spriteBatch);

        SoundManager.getSoundManager().bgmPlay(this.bAnotherStory ? SoundInfo.BGM_ANOTHERSTAGESELECT : SoundInfo.BGM_STAGESELECT);

    }

    /**
     * @brief データの読み込み処理を行う
     */
    public void load(){

        if(!this.bAnotherStory){

            String[] paths = {"StageText/Stage1.txt", "StageText/Stage2.txt", "StageText/Stage3.txt", "StageText/Stage4.txt", "StageText/Stage5.txt", "StageText/Stage6.txt"};

            //ステージパスの初期化
            for(String path : paths){

                this.stagePath.add(path);

            }

            //プレイヤーサイズの初期化
            this.size = new Vector2(60f, 64f);

            Texture texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PLAYER);

            AnimeData[] animeData = {new AnimeData(texture,"待機", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0}}, 0,0,0.05f),
                    new AnimeData(texture,"移動", size,true, new int[][]{{0,0},{1,0},{2,0},{3,0},{4,0},{5,0}}, 0,70,0.05f)};

            //アニメーションの初期化
            this.anime = new AnimeMotion(animeData, this.spriteBatch);

        }else{

            String[] paths = {"AnotherStageText/Stage1.txt", "AnotherStageText/Stage2.txt", "AnotherStageText/Stage3.txt"};

            //paths[0] = "Stage6.txt";

            //ステージパスの初期化
            for(String path : paths){

                this.stagePath.add(path);

            }

            //プレイヤーサイズの初期化
            this.size = new Vector2(64f, 64f);

            Texture texture = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_PUMPKING);

            AnimeData[] animeData = {new AnimeData(texture,"待機", size,true, new int[][]{{0,0},{1,0},{2,0}}, 0,0,0.05f),
                    new AnimeData(texture,"移動", size,true, new int[][]{{0,2},{1,2},{2,2}}, 0,0,0.05f)};

            //アニメーションの初期化
            this.anime = new AnimeMotion(animeData, this.spriteBatch);

        }

    }

    /**
     * @brief 初期化処理を行う
     */
    public void initialize(){

        if(this.bAnotherStory){

            anotherPlayerInitialize();

        }else{

            playerInitialize();

        }

    }

    /**
     * @brief プレイヤーの初期化を行う
     */
    public void playerInitialize(){

        Vector2[] stagePos = {new Vector2(555f, 534f), new Vector2(76f, 24f), new Vector2(288f, 188f), new Vector2(728f, 638f), new Vector2(703f, 143f), new Vector2(220f, 380f)};

        //ステージ座標の初期化
        for(Vector2 pos : stagePos){

            this.stagePos.add(pos);

        }

        String[] names = {TextureInfo.TEXTURE_STAGE1TITLE, TextureInfo.TEXTURE_STAGE2TITLE, TextureInfo.TEXTURE_STAGE3TITLE, TextureInfo.TEXTURE_STAGE4TITLE, TextureInfo.TEXTURE_STAGE5TITLE, TextureInfo.TEXTURE_STAGE6TITLE};

        //ステージ名とステージ名座標の初期化
        for(String name : names){

            this.stageName.add(name);

            Texture texture = TextureManager.getTextureManager().getTexture(name);

            float x = Utiility.getScreenCenterX(texture.getWidth());

            float y = this.camera.viewportHeight - texture.getHeight();

            this.stageNamePos.add(new Vector2(x, y));

        }

        //ステージの色の初期化
        for(int i = 0;i < STAGE_COUNT;i++){

            this.stageColor.add(Color.RED);

        }

        //プレイヤーの座標の初期化
        this.playerPos = new Vector2(this.stagePos.get(this.stageNo));

        this.playerPos.sub(this.size.x * 0.5f, this.size.y * 0.5f);

    }

    /**
     * @brief アナザープレイヤーの初期化を行う
     */
    public void anotherPlayerInitialize(){

        Vector2[] stagePos = {new Vector2(931f, 127f), new Vector2(360f, 357f), new Vector2(439f, 604f)};

        //ステージ座標の初期化
        for(Vector2 pos : stagePos){

            this.stagePos.add(pos);

        }

        String[] names = {TextureInfo.TEXTURE_ANOTHERSTAGE1TITLE, TextureInfo.TEXTURE_ANOTHERSTAGE2TITLE, TextureInfo.TEXTURE_ANOTHERSTAGE3TITLE};

        //ステージ名とステージ名座標の初期化
        for(String name : names){

            this.stageName.add(name);

            Texture texture = TextureManager.getTextureManager().getTexture(name);

            float x = Utiility.getScreenCenterX(texture.getWidth());

            float y = this.camera.viewportHeight - texture.getHeight();

            this.stageNamePos.add(new Vector2(x, y));

        }

        //ステージの色の初期化
        for(int i = 0;i < ANOTHERSTAGE_COUNT;i++){

            this.stageColor.add(Color.RED);

        }

        //プレイヤーの座標の初期化
        this.playerPos = new Vector2(this.stagePos.get(this.stageNo));

        this.playerPos.sub(this.size.x * 0.5f, this.size.y * 0.5f);

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime 経過時間
     */
    public void update(float deltaTime){

        //遷移エフェクトの更新
        ScreenEffectManager.getScreenEffectManager().rectOrderLined(ScreenElement.SCREEN_GAMEBREAK);

        //クリアステージの更新
        setClearStage();

        //操作の更新
        operation();

        //モーション変更の更新
        motionChange();

        this.anime.addTime(deltaTime);

    }

    /**
     * @brief 操作の処理を行う
     */
    private void operation(){

        if(ScreenElement.getElement().isEffect()){

            return;

        }

        //バックボタンでメニュースクリーンへ遷移
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){

            ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_MENU);

        }

        if(this.pause.update()){

            return;

        }

        //移動操作中の場合
        if(this.bOperation){

            stageCollistion();

            return;

        }

        ArrayList<Integer> list = InputManager.getInputManager().getTouchDownList();

        int maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++){

            int pointer = list.get(i);

            this.touchPos.x = Gdx.input.getX(pointer);
            this.touchPos.y = Gdx.input.getY(pointer);

            this.touchPos = this.viewport.unproject(this.touchPos);

            for(int touch = 0;touch <= this.clearNo;touch++){

                this.stageCircle.set(this.stagePos.get(touch).x, this.stagePos.get(touch).y, this.TOUCH_CIRCLERADIUS[touch]);

                if(this.stageCircle.contains(this.touchPos.x, this.touchPos.y)){

                    if(touch == this.stageNo){

                        GameElement.getGameElement().setStagePath(this.stagePath.get(touch));

                        GameElement.getGameElement().setSelectStageNo(touch);

                        ScreenElement.getElement().setEffect(true);

                        SoundManager.getSoundManager().allStop();

                        return;

                    }

                    this.bOperation = true;

                    this.stageNo = touch;

                    Vector2 startPos = new Vector2(this.playerPos.x + this.size.x * 0.5f, this.playerPos.y + this.size.y * 0.5f);

                    Vector2 dd = new Vector2(this.stagePos.get(this.stageNo).x - startPos.x, this.stagePos.get(this.stageNo).y - startPos.y);

                    float d = (float)Math.sqrt(dd.x * dd.x + dd.y * dd.y);

                    if(d <= 0){

                        return;

                    }

                    dd.x /= d;
                    dd.y /= d;

                    this.move.set(dd.scl(5f, 5f));

                    break;

                }

            }

        }

    }

    /**
     * @brief ステージ衝突判定の処理を行う
     */
    private void stageCollistion(){

        this.playerPos.add(this.move);

        this.stageCircle.set(this.stagePos.get(this.stageNo).x, this.stagePos.get(this.stageNo).y, 5.0f);

        this.playerCircle.set(this.playerPos.x + (this.size.x * 0.5f), this.playerPos.y + (this.size.y * 0.5f), 5f);

        //プレイヤーとステージの円が衝突した場合
        if(this.stageCircle.overlaps(this.playerCircle)){

            this.move.set(0f, 0f);

            this.bOperation = false;

        }

    }

    /**
     * @brief クリアステージの設定を行う
     */
    private void setClearStage(){

        //現在のクリアステージ
        if(this.nowClearNo < this.clearNo && this.clearNo <= this.stageCount){

            this.nowClearNo = this.clearNo;

        }

        for(int i = 0;i < this.nowClearNo;i++){

            this.stageColor.get(i).set(Color.GREEN);

        }

    }

    /**
     * @brief モーション変更の処理を行う
     */
    private void motionChange(){

        //移動している場合
        if(this.move.x > 0){

            this.bReverse = false;

            this.anime.changeAnime(ANIME_MOVE);

        }else if(this.move.x < 0){

            this.bReverse = true;

            this.anime.changeAnime(ANIME_MOVE);

         //待機している場合
        }else{

            this.bReverse = false;

            this.anime.changeAnime(ANIME_WAIT);

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

        Texture backGround;

        if(this.bAnotherStory){

            backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_ANOTHERSTAGESELECTBACKGROUND);

        }else{

            if(this.clearNo > GameElement.STAGE5){

                backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_BOSSSTAGESELECTBACKGROUND);

            }else{

                backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_STAGESELECTBACKGROUND);

            }

        }

        //背景の描画
        this.spriteBatch.draw(backGround, 0,0);

        this.spriteBatch.end();

        this.renderer.begin(ShapeRenderer.ShapeType.Filled);

        this.renderer.setProjectionMatrix(this.camera.combined);

        int stageCount = this.bAnotherStory ? this.stageColor.size() : this.stageColor.size() - 1;

        //ステージの描画
        for(int i = 0;i <= this.clearNo && i < stageCount;i++){

            this.renderer.setColor(this.stageColor.get(i));

            this.renderer.circle(this.stagePos.get(i).x, this.stagePos.get(i).y, 10f);

        }

        this.renderer.end();

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        //プレイヤーの描画
        this.anime.render(this.playerPos.x, this.playerPos.y, this.bReverse, false);

        Texture stageName = TextureManager.getTextureManager().getTexture(this.stageName.get(this.stageNo));

        //ステージ名の描画
        this.spriteBatch.draw(stageName, this.stageNamePos.get(this.stageNo).x, this.stageNamePos.get(this.stageNo).y);

        this.spriteBatch.end();

        //ポーズの描画
        this.pause.render();

        //遷移エフェクトの描画
        ScreenEffectManager.getScreenEffectManager().rectOrderLinedRender(this.camera.combined);

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
