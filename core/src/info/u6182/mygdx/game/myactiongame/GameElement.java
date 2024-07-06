/**
 * @file GameElement.java
 * @brief ゲーム内の基本的な要素を管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame;

import com.badlogic.gdx.math.Vector2;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import info.u6182.api.Utiility;

/**
 * @class GameElement
 * @brief ゲーム内の基本的な要素を管理するクラス
 */
public class GameElement {

    //! 重力
    public static final float GRAVITY = -0.3f;

    //! ストーリークリア番号
    public static final int STORY_CLEAR = 1;

    //! アナザーストーリークリア番号
    public static final int ANOTHERSTORY_CLEAR = 2;

    //! 各地位
    public static final String[] STATUS_NAME = {"奴隷", "平民", "兵士", "勇者", "王"};

    //! 地位に必要な名声
    public static final int[] STATUS_FAMESCORE = {GameElement.PLAYERSTATUS_SLAVE, GameElement.PLAYERSTATUS_COMMONER, GameElement.PLAYERSTATUS_SOLDIER, GameElement.PLAYERSTATUS_HERO, GameElement.PLAYERSTATUS_KING};

    //! 分岐ストーリークリア番号
    public static final int BRANCHSTORY_CLEAR = 3;

    //! PAUSEでない状態
    public static final int PAUSE_NOT = 0;

    //! PAUSE中の状態
    public static final int PAUSE_RUNNING = 1;

    //! 奴隷のステータス
    public static final int PLAYERSTATUS_SLAVE = 1000;

    //! 平民のステータス
    public static final int PLAYERSTATUS_COMMONER = 3000;

    //! 兵士のステータス
    public static final int PLAYERSTATUS_SOLDIER = 5000;

    //! 勇者のステータス
    public static final int PLAYERSTATUS_HERO = 10000;

    //! 王のステータス
    public static final int PLAYERSTATUS_KING = 15000;

    //! ステージ1番号
    public static final int STAGE1 = 0;

    //! ステージ2番号
    public static final int STAGE2 = 1;

    //! ステージ3番号
    public static final int STAGE3 = 2;

    //! ステージ4番号
    public static final int STAGE4 = 3;

    //! ステージ5番号
    public static final int STAGE5 = 4;

    //! ステージ6番号
    public static final int STAGE6 = 5;

    //! ステージ数
    public static final int STAGE_COUNT = 6;

    //! パンプキンの番号
    public static final int ENEMY_PUMPKIN = 0;

    //! グリーンパンプキンの番号
    public static final int ENEMY_GREENPUMPKIN = 1;

    //! ゴーストパンプキンの番号
    public static final int ENEMY_GHOSTPUMPKIN = 2;

    //! ホワイトゴーストの番号
    public static final int ENEMY_WHITEGHOST = 3;

    //! ブラックゴーストの番号
    public static final int ENEMY_BLACKGHOST = 4;

    //! キングゴーストの番号
    public static final int ENEMY_KINGGHOST = 5;

    //! パンプキングの番号
    public static final int ENEMY_PUMPKING = 6;

    //! 敵のカウント
    public static final int ENEMY_COUNT = 7;

    //! ファイアバードマンの番号
    public static final int ENEMY_FIREBIRDMAN = 0;

    //! アイスバードマンの番号
    public static final int ENEMY_ICEBIRDMAN = 1;

    //! ウッドバードマンの番号
    public static final int ENEMY_WOODBIRDMAN = 2;

    //! 戦士の番号
    public static final int ENEMY_WARRIOR = 3;

    //! 黄金戦士の番号
    public static final int ENEMY_GOLDWARRIOR = 4;

    //! サキュバスの番号
    public static final int ENEMY_SUCCUBUS = 5;

    //! 魔導士の番号
    public static final int ENEMY_MAGE = 6;

    //! ウルフ(右)の番号
    public static final int ENEMY_WOLFRIGHT = 7;

    //! ウルフ(左)の番号
    public static final int ENEMY_WOLFLEFT = 8;

    //! アサシン・ウルフの番号
    public static final int ENEMY_ASSASSINWOLF = 9;

    //! 非道の王の番号
    public static final int ENEMY_HELLISHESTKING = 10;

    //! ゴールドゴールアイテムの番号
    public static final int ITEM_GOLDGOAL = 0;

    //! シルバーゴールアイテムの番号
    public static final int ITEM_SILVERGOAL = 1;

    //! アナザーゴールアイテムの番号
    public static final int ITEM_ANOTHERGOAL = 2;

    //! アナザー分岐ゴールアイテムの番号
    public static final int ITEM_ANOTHERBRANCHGOAL = 3;

    //! アサシン・ウルフクリアアイテムの番号
    public static final int ITEM_ASSASSINCLEARGOAL = 4;

    //! クリアアイテムの番号
    public static final int ITEM_CLEARGOAL = 5;

    //! パンプキンの名声スコア
    public static final int FAMESCORE_PUMPKIN = 50;

    //! グリーンパンプキンの名声スコア
    public static final int FAMESCORE_GREENPUMPKIN = 150;

    //! ゴーストパンプキンの名声スコア
    public static final int FAMESCORE_GHOSTPUMPKIN = 250;

    //! ホワイトゴーストの名声スコア
    public static final int FAMESCORE_WHITEGHOST = 50;

    //! ブラックゴーストの名声スコア
    public static final int FAMESCORE_BLACKGHOST = 150;

    //! キングゴーストの名声スコア
    public static final int FAMESCORE_KINGGHOST = 250;

    //! パンプキングの名声スコア
    public static final int FAMESCORE_PUMPKING = 500;

    //! パンプキンの召喚コスト
    public static final int CALLCOST_PUMPKIN = 20;

    //! グリーンパンプキンの召喚コスト
    public static final int CALLCOST_GREENPUMPKIN = 30;

    //! ゴーストパンプキンの召喚コスト
    public static final int CALLCOST_GHOSTPUMPKIN = 50;

    //! ホワイトゴーストの召喚コスト
    public static final int CALLCOST_WHITEGHOST = 25;

    //! ブラックゴーストの召喚コスト
    public static final int CALLCOST_BLACKGHOST = 30;

    //! キングゴーストの召喚コスト
    public static final int CALLCOST_KINGGHOST = 100;

    //! シルバーゴールアイテムの名声スコア
    public static final int FAMESCORE_SILVERGOAL = 100;

    //! ゴールドゴールアイテムの名声スコア
    public static final int FAMESCORE_GOLDGOAL = 200;

    //! クリアアイテムの名声スコア
    public static final int FAMESCORE_CLEARGOAL = 1000;

    //! プレイヤーの残機
    private final int PLAYER_LIFE = 3;

    //! 自身のインスタンス
    private static final GameElement gameElement = new GameElement();

    //! 暗号復号化キー
    private SecretKey key;

    //! 初期化ベクトル
    private IvParameterSpec iv;

    //! 設定
    private Settings settings;

    //! ステージのファイルパス
    private String stagePath;

    //! ビューポートサイズ
    private Vector2 viewPortSize;

    //! スクロール
    private Vector2 scroll;

    //! カメラ座標
    private Vector2 cameraPos;

    //! アナザーストーリーの解除フラグ
    private boolean bStoryLifted;

    //! アナザーストーリーフラグ
    private boolean bAnotherStory;

    //! ゲームクリアアイテム取得フラグ
    private boolean bClearItem;

    //! クリアしたストーリー
    private int storyClear;

    //! プレイヤーの残機
    private int life;

    //! アナザープレイヤーの残機
    private int anotherLife;

    //! 現在のメニュー番号
    private int menuNo;

    //! 選択したステージ番号
    private int selectStageNo;

    //! 選択したアナザーステージ番号
    private int anotherSelectStageNo;

    //! クリアしたステージ番号
    private int clearStageNo;

    //! 過去最高のクリアステージ番号
    private int highClearStageNo;

    //! クリアしたアナザーステージ番号
    private int anotherClearStageNo;

    //! 名声スコア
    private int fameScore;

    //! ステージ名声スコア
    private int[] stageFameScore;

    /**
     * @brief コンストラクタ
     */
    private GameElement(){

        //ビューポートサイズの初期化
        this.viewPortSize = new Vector2(0,0);

        //カメラ移動量の初期化
        this.scroll = new Vector2(0,0);

        //カメラ座標の初期化
        this.cameraPos = new Vector2(0,0);

        //アナザーストーリーの解除フラグの初期化
        this.bStoryLifted = false;

        //アナザーストーリーフラグの初期化
        this.bAnotherStory = false;

        //ゲームクリアアイテム取得フラグの初期化
        this.bClearItem = false;

        //クリアしたストーリーの初期化
        this.storyClear = 0;

        //プレイヤーの残機の初期化
        this.life = PLAYER_LIFE;

        //アナザープレイヤーの残機の初期化
        this.anotherLife = PLAYER_LIFE;

        //現在のメニュー番号の初期化
        this.menuNo = 0;

        //選択したステージ番号の初期化
        this.selectStageNo = 0;

        //選択したアナザーステージ番号の初期化
        this.anotherSelectStageNo = 0;

        //クリアしたステージ番号の初期化
        this.clearStageNo = 5;

        //過去最高のクリアステージ番号の初期化
        this.highClearStageNo = 0;

        //クリアしたアナザーステージ番号の初期化
        this.anotherClearStageNo = 2;

        //ステージ名声スコアの初期化
        this.stageFameScore = new int[STAGE_COUNT];

        for(int fameScore : this.stageFameScore){

            fameScore = 0;

        }

    }

    /**
     * @brief 設定やデータの読み込みを行う
     */
    public void Load(){

        SecretKey key = Load.getLoad().keyLoad();

        this.key = key == null ? Utiility.generateKey() : key;

        IvParameterSpec iv = Load.getLoad().ivLoad();

        this.iv = iv == null ? Utiility.generateIV() : iv;

        Settings settings = Load.getLoad().settingsLoad(key, iv);

        this.settings = settings == null ? new Settings() : settings;

        this.highClearStageNo = Load.getLoad().highClearStageNoLoad(key, iv);

    }

    /**
     * @brief 暗号化キーの取得を行う
     * @return SecretKey 暗号化キー
     */
    public SecretKey getKey(){

        return this.key;

    }

    /**
     * @brief 初期化ベクトルの取得を行う
     * @return IvParameterSpec 初期化ベクトル
     */
    public IvParameterSpec getIv(){

        return this.iv;

    }

    /**
     * @brief ゲーム要素の初期化を行う
     */
    public void initialize(){

        //ゲームクリアアイテム取得フラグの初期化
        this.bClearItem = false;

        //クリアしたストーリーの初期化
        this.storyClear = 0;

        if(this.bAnotherStory){

            //アナザープレイヤーの残機の初期化
            this.anotherLife = PLAYER_LIFE;

            //アナザーステージ番号の初期化
            this.anotherSelectStageNo = 0;

            //アナザーステージのクリア番号の初期化
            this.anotherClearStageNo = 0;

        }else{

            //プレイヤーの残機の初期化
            this.life = PLAYER_LIFE;

            //選択したステージ番号の初期化
            this.selectStageNo = 0;

            //ステージのクリア番号の初期化
            this.clearStageNo = 0;

        }

        //ステージ名声スコアの初期化
        for(int fameScore : this.stageFameScore){

            fameScore = 0;

        }

    }

    /**
     * @brief ビューポートサイズの設定を行う
     * @param width 幅
     * @param height 高さ
     */
    public void setViewPortSize(float width, float height){

        this.viewPortSize.set(width, height);

    }

    /**
     * @brief ステージパスの設定を行う
     * @param path ステージパス
     */
    public void setStagePath(String path){

        this.stagePath = path;

    }

    /**
     * @brief アナザーストーリー解除フラグの設定を行う
     * @param bStoryLifted アナザーストーリー解除フラグ
     */
    public void setStoryLifted(boolean bStoryLifted){

        this.bStoryLifted = bStoryLifted;

    }

    /**
     * @brief アナザーストーリーフラグの設定を行う
     * @param bAnotherStory アナザーストーリーフラグ
     */
    public void setAnotherStory(boolean bAnotherStory){

        this.bAnotherStory = bAnotherStory;

    }

    /**
     * @brief ゲームクリアアイテム取得フラグの設定を行う
     * @param bClearItem ゲームクリアアイテム取得フラグ
     */
    public void setClearItem(boolean bClearItem){

        this.bClearItem = bClearItem;

    }

    /**
     * @brief クリアしたストーリーの設定を行う
     * @param storyClear クリアしたストーリー
     */
    public void setStoryClear(int storyClear){

        this.storyClear = storyClear;

    }

    /**
     * @brief 残機の設定を行う
     * @param life 残機
     */
    public void setLife(int life){

        if(this.bAnotherStory){

            this.anotherLife = life;

        }else{

            this.life = life;

        }

    }

    /**
     * @brief 現在のメニュー番号の設定を行う
     * @param menuNo 現在のメニュー番号
     */
    public void setMenuNo(int menuNo){

        this.menuNo = menuNo;

    }

    /**
     * @brief 選択したステージ番号の設定を行う
     * @param selectStageNo 選択したステージ番号
     */
    public void setSelectStageNo(int selectStageNo){

        if(this.bAnotherStory){

            this.anotherSelectStageNo = selectStageNo;

        }else{

            this.selectStageNo = selectStageNo;

        }

    }

    /**
     * @brief クリアしたステージ番号の設定を行う
     * @param clearStageNo クリアしたステージ番号
     */
    public void setClearStageNo(int clearStageNo){

        if(this.bAnotherStory){

            this.anotherClearStageNo = clearStageNo;

        }else{

            this.clearStageNo = clearStageNo;

        }

    }

    /**
     * @brief 過去最高のクリアステージ番号の設定を行う
     * @param highClearStageNo 過去最高のクリアステージ番号
     */
    public void setHighClearStageNo(int highClearStageNo){

        this.highClearStageNo = highClearStageNo;

    }

    /**
     * @brief ステージ名声スコアの設定を行う
     * @param stageNo ステージ番号
     * @param fameScore 名声スコア
     */
    public void setStageFameScore(int stageNo, int fameScore){

        this.stageFameScore[stageNo] = fameScore;

    }

    /**
     * @brief GameElementのインスタンスの取得を行う
     * @return GameElementのインスタンス
     */
    public static GameElement getGameElement(){

        return gameElement;

    }

    /**
     * @brief 設定の取得を行う
     * @return Settings 設定
     */
    public Settings getSettings(){

        return this.settings;

    }

    /**
     * @brief ビューポートサイズの取得を行う
     * @return Vector2 ビューポートサイズ
     */
    public Vector2 getViewPortSize(){

        return this.viewPortSize;

    }

    /**
     * @brief スクロール量の取得を行う
     * @return Vector2 スクロール量
     */
    public Vector2 getScroll(){

        return this.scroll;

    }

    /**
     * @brief カメラ座標の取得を行う
     * @return Vector2 カメラ座標
     */
    public Vector2 getCameraPos(){

        return this.cameraPos;

    }

    /**
     * @brief ステージパスの取得を行う
     * @return String ステージパス
     */
    public String getStagePath(){

        return this.stagePath;

    }

    /**
     * @brief アナザーストーリー解除フラグの取得を行う
     * @return boolean アナザーストーリー解除フラグ
     */
    public boolean isStoryLifted(){

        return this.bStoryLifted;

    }

    /**
     * @brief アナザーストーリーフラグの取得を行う
     * @return boolean アナザーストーリーフラグ
     */
    public boolean isAnotherStory(){

        return this.bAnotherStory;

    }

    /**
     * @brief ゲームクリアアイテム取得フラグの取得を行う
     * @return boolean ゲームクリアアイテム取得フラグ
     */
    public boolean isClearItem(){

        return this.bClearItem;

    }

    /**
     * @brief クリアしたストーリーの取得を行う
     * @return int クリアしたストーリー
     */
    public int getStoryClear(){

        return this.storyClear;

    }

    /**
     * @brief 残機の取得を行う
     * @return int 残機
     */
    public int getLife(){

        return this.bAnotherStory ? this.anotherLife : this.life;

    }

    /**
     * @brief 現在のメニュー番号の取得を行う
     * @return int 現在のメニュー番号
     */
    public int getMenuNo(){

        return this.menuNo;

    }

    /**
     * @brief 選択したステージ番号の取得を行う
     * @return int 選択したステージ番号
     */
    public int getSelectStageNo(){

        return this.bAnotherStory ? this.anotherSelectStageNo : this.selectStageNo;

    }

    /**
     * @brief クリアしたステージ番号の取得を行う
     * @return int クリアしたステージ番号
     */
    public int getClearStageNo(){

        return this.bAnotherStory ? this.anotherClearStageNo : this.clearStageNo;

    }

    /**
     * @brief 過去最高のクリアステージ番号の取得を行う
     * @return int 過去最高のクリアステージ番号
     */
    public int getHighClearStageNo(){

        return this.highClearStageNo;

    }

    /**
     * @brief ステージ名声スコアの取得を行う
     * @return int[] ステージ名声スコア
     */
    public int[] getStageFameScore(){

        return this.stageFameScore;

    }

}
