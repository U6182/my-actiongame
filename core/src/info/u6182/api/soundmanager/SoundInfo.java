/**
 * @file SoundInfo.java
 * @brief サウンド情報管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.soundmanager;

import info.u6182.api.AssetInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;

import java.util.ArrayList;

/**
 * @class SoundInfo
 * @brief サウンド情報管理クラス
 */
public class SoundInfo extends AssetInfo {

    //! 攻撃サウンド
    public static String SOUND_ATTACK = "SoundEffect/Attack.mp3";

    //! ボスの爆発サウンド
    public static String SOUND_PUMPKINGBLASTING = "SoundEffect/BossBlasting.mp3";

    //! ボスの踏みつけサウンド
    public static String SOUND_PUMPKINGSTEPON = "SoundEffect/BossStepon.mp3";

    //! 召喚サウンド
    public static String SOUND_CALL = "SoundEffect/Call.mp3";

    //! クリックサウンド
    public static String SOUND_CLICK = "SoundEffect/Click.mp3";

    //! 呪いサウンド
    public static String SOUND_CURSE = "SoundEffect/Curse.mp3";

    //! 呪い状態サウンド
    public static String SOUND_CURSESTATE = "SoundEffect/CurseState.mp3";

    //! 爆発サウンド
    public static String SOUND_EXPLOSION = "SoundEffect/Explosion.mp3";

    //! 爆発壁サウンド
    public static String SOUND_EXPLOSIONWALL = "SoundEffect/ExplosionWall.mp3";

    //! 失敗サウンド
    public static String SOUND_FAIL = "SoundEffect/Fail.mp3";

    //! 名声サウンド
    public static String SOUND_FAME = "SoundEffect/Fame.mp3";

    //! 火の玉サウンド
    public static String SOUND_FIRESHOT = "SoundEffect/FireShot.mp3";

    //! 氷魔法サウンド
    public static String SOUND_ICEMAGIC = "SoundEffect/IceMagic.mp3";

    //! 氷壁サウンド
    public static String SOUND_ICEWALL = "SoundEffect/IceWall.mp3";

    //! ジャンプサウンド
    public static String SOUND_JUMP = "SoundEffect/Jump.mp3";

    //! リフテッドサウンド
    public static String SOUND_LIFTED = "SoundEffect/Lifted.mp3";

    //! 魔導士の誘惑サウンド
    public static String SOUND_MAGETEMPTATION = "SoundEffect/MageTemptation.mp3";

    //! 魔法サウンド
    public static String SOUND_MAGIC = "SoundEffect/Magic.mp3";

    //! 岩壁サウンド
    public static String SOUND_ROCKWALL = "SoundEffect/RockWall.mp3";

    //! 選択サウンド
    public static String SOUND_SELECT = "SoundEffect/Select.mp3";

    //! 断絶サウンド
    public static String SOUND_SEVER = "SoundEffect/Sever.mp3";

    //! 王の状態サウンド
    public static String SOUND_STATUSKING = "SoundEffect/StatusKing.mp3";

    //! 状態アップサウンド
    public static String SOUND_STATUSUP = "SoundEffect/StatusUp.mp3";

    //! ストーリーページサウンド
    public static String SOUND_STORYPAGE = "SoundEffect/StoryPage.mp3";

    //! 誘惑サウンド
    public static String SOUND_TEMPTAION = "SoundEffect/Temptation.mp3";

    //! 誘惑状態サウンド
    public static String SOUND_TEMPTATIONSTATE = "SoundEffect/TemptationState.mp3";

    //! 雷魔法サウンド
    public static String SOUND_THUNDERMAGIC = "SoundEffect/ThunderMagic.mp3";

    //! 雷壁サウンド
    public static String SOUND_THUNDERWALL = "SoundEffect/ThunderWall.mp3";

    //! 風獣サウンド
    public static String SOUND_WINDBEAST = "SoundEffect/WindBeast.mp3";

    //! 風魔法サウンド
    public static String SOUND_WINDMAGIC = "SoundEffect/WindMagic.mp3";

    //! 暗殺者の狼開始BGM
    public static String BGM_ASSASSINWOLFBEGIN = "BGM/AssassinWolfBeginBGM.mp3";

    //! 別ステージ1BGM
    public static String BGM_ANOTHERSTAGE1 = "BGM/BackStage1BGM.mp3";

    //! 別ステージ2BGM
    public static String BGM_ANOTHERSTAGE2 = "BGM/BackStage2BGM.mp3";

    //! 別ステージ3BGM
    public static String BGM_ANOTHERSTAGE3 = "BGM/BackStage3BGM.mp3";

    //! 別ステージ選択BGM
    public static String BGM_ANOTHERSTAGESELECT = "BGM/BackStageSelectBGM.mp3";

    //! ボス開始BGM
    public static String BGM_BOSSBEGIN = "BGM/BossBeginBGM.mp3";

    //! ボスBGM
    public static String BGM_BOSS = "BGM/BossBGM.mp3";

    //! 死亡BGM
    public static String BGM_DEAD = "BGM/DeadBGM.mp3";

    //! ゲームオーバーBGM
    public static String BGM_GAMEOVER = "BGM/GameOverBGM.mp3";

    //! 非道の王開始BGM
    public static String BGM_HELLISHESTKINGBEGIN = "BGM/HellishestKingBeginBGM.mp3";

    //! プレイヤー交代BGM
    public static String BGM_PLAYERCHANGE = "BGM/PlayerChangeBGM.mp3";

    //! ステージ2BGM
    public static String BGM_STAGE2 = "BGM/Stage2BGM.mp3";

    //! ステージ3BGM
    public static String BGM_STAGE3 = "BGM/Stage3BGM.mp3";

    //! ステージ4BGM
    public static String BGM_STAGE4 = "BGM/Stage4BGM.mp3";

    //! ステージ5BGM
    public static String BGM_STAGE5 = "BGM/Stage5BGM.mp3";

    //! ステージBGM
    public static String BGM_STAGE = "BGM/StageBGM.mp3";

    //! ステージ選択BGM
    public static String BGM_STAGESELECT = "BGM/StageSelectBGM.mp3";

    //! オプションBGM
    public static String BGM_OPTION = "BGM/OptionBGM.mp3";

    /**
     * @brief コンストラクタ
     * @details 生成時に呼び出し、メンバの初期化を行う
     */
    public SoundInfo(){

        this.pathList = new ArrayList<>();

    }

    /**
     * @brief titleLoad
     * @details タイトル画面のサウンドのロードを行う
     */
    public void titleLoad(){

        this.pathList.add(SOUND_CLICK);

    }

    /**
     * @brief menuLoad
     * @details メニュー画面のサウンドのロードを行う
     */
    public void menuLoad(){

        this.pathList.add(SOUND_STORYPAGE);

        this.pathList.add(SOUND_CLICK);

        this.pathList.add(SOUND_FAIL);

    }

    /**
     * @brief stageSelectLoad
     * @details ステージ選択画面のサウンドのロードを行う
     */
    public void stageSelectLoad(){

        this.pathList.add(SOUND_CLICK);

    }

    /**
     * @brief doorHatredLoad
     * @details 憎しみの扉のサウンドのロードを行う
     */
    public void doorHatredLoad(){

        this.pathList.add(SOUND_LIFTED);

        this.pathList.add(SOUND_FAIL);

    }

    /**
     * @brief gameLoad
     * @details ゲーム内のサウンドのロードを行う
     */
    public void gameLoad(){

        this.pathList.add(SOUND_JUMP);

        this.pathList.add(SOUND_ATTACK);

        int stageNo = GameElement.getGameElement().getSelectStageNo();

        if(GameElement.getGameElement().isAnotherStory()){

            this.pathList.add(SOUND_CALL);

            switch (stageNo){

                case GameElement.STAGE1:

                    anotherStage1Load();

                    break;

                case GameElement.STAGE2:

                    anotherStage2Load();

                    break;

                case GameElement.STAGE3:

                    anotherStage3Load();

                    break;

            }

        }else{

            this.pathList.add(SOUND_FIRESHOT);

            this.pathList.add(SOUND_FAME);

            this.pathList.add(SOUND_STATUSUP);

            this.pathList.add(SOUND_STATUSKING);

            if(stageNo == GameElement.STAGE6){

                stage6Load();

            }

        }

    }

    /**
     * @brief stage6Load
     * @details ステージ6のサウンドのロードを行う
     */
    public void stage6Load(){

        this.pathList.add(SOUND_PUMPKINGBLASTING);

        this.pathList.add(SOUND_PUMPKINGSTEPON);

    }

    /**
     * @brief anotherStage1Load
     * @details 別ストーリーのステージ1のサウンドのロードを行う
     */
    public void anotherStage1Load(){

        this.pathList.add(SOUND_MAGETEMPTATION);

        this.pathList.add(SOUND_CURSE);

        this.pathList.add(SOUND_TEMPTAION);

        this.pathList.add(SOUND_CURSESTATE);

        this.pathList.add(SOUND_TEMPTATIONSTATE);

        this.pathList.add(SOUND_ICEWALL);

        this.pathList.add(SOUND_THUNDERWALL);

        this.pathList.add(SOUND_ROCKWALL);

        this.pathList.add(SOUND_SEVER);

    }

    /**
     * @brief anotherStage2Load
     * @details 別ストーリーのステージ2のサウンドのロードを行う
     */
    public void anotherStage2Load(){

        this.pathList.add(SOUND_SEVER);

    }

    /**
     * @brief anotherStage3Load
     * @details 別ストーリーのステージ3のサウンドのロードを行う
     */
    public void anotherStage3Load(){

        this.pathList.add(SOUND_THUNDERWALL);

        this.pathList.add(SOUND_ICEWALL);

        this.pathList.add(SOUND_ROCKWALL);

        this.pathList.add(SOUND_EXPLOSIONWALL);

        this.pathList.add(SOUND_WINDBEAST);

        this.pathList.add(SOUND_EXPLOSION);

        this.pathList.add(SOUND_THUNDERMAGIC);

        this.pathList.add(SOUND_ICEMAGIC);

        this.pathList.add(SOUND_WINDMAGIC);

        this.pathList.add(SOUND_MAGIC);

    }

    /**
     * @brief gameOverLoad
     * @details ゲームオーバー画面のサウンドのロードを行う
     */
    public void gameOverLoad(){
        ;
    }

    /**
     * @brief optionLoad
     * @details オプション画面のサウンドのロードを行う
     */
    public void optionLoad(){

        this.pathList.add(SOUND_SELECT);

    }

    /**
     * @brief fameScoreLoad
     * @details 名声スコア画面のサウンドのロードを行う
     */
    public void fameScoreLoad(){

        this.pathList.add(SOUND_CLICK);

    }

}
