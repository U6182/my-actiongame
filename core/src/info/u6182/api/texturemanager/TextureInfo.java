/**
 * @file TextureInfo.java
 * @brief テクスチャ情報管理クラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.texturemanager;

import info.u6182.api.AssetInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;

import java.util.ArrayList;

/**
 * @class TextureInfo
 * @brief テクスチャ情報管理クラス
 * @details 各テクスチャのパス情報を管理し、ロード機能を提供するクラス
 */
public class TextureInfo extends AssetInfo {

    //! プレイヤーテクスチャ
    public static final String TEXTURE_PLAYER = "Player/player.png";

    //! ファイアボールテクスチャ
    public static final String TEXTURE_FIREBALL = "Player/Fireball.png";

    //! 敵テクスチャ：パンプキン
    public static final String TEXTURE_PUMPKIN = "Enemy/Pumpkin.png";

    //! 敵テクスチャ：グリーンパンプキン
    public static final String TEXTURE_GREENPUMPKIN = "Enemy/GreenPumpkin.png";

    //! 敵テクスチャ：ゴーストパンプキン
    public static final String TEXTURE_GHOSTPUMPKIN = "Enemy/GhostPumpkin.png";

    //! 敵テクスチャ：ホワイトゴースト
    public static final String TEXTURE_WHITEGHOST = "Enemy/WhiteGhost.png";

    //! 敵テクスチャ：ブラックゴースト
    public static final String TEXTURE_BLACKGHOST = "Enemy/BlackGhost.png";

    //! 敵テクスチャ：キングゴースト
    public static final String TEXTURE_KINGGHOST = "Enemy/KingGhost.png";

    //! 敵テクスチャ：パンプキング
    public static final String TEXTURE_PUMPKING = "Enemy/PumpKing.png";

    //! ステージチップテクスチャ
    public static final String TEXTURE_STAGECHIP = "Stage/Stage.png";

    //! タイトルテクスチャ
    public static final String TEXTURE_TITLEL = "Title/Title.png";

    //! タイトルタッチテクスチャ
    public static final String TEXTURE_TITLETOUCH = "Title/TitleTouch.png";

    //! タイトル背景テクスチャ
    public static final String TEXTURE_TITLEBACKBACKGROUND = "BackGround/Title.png";

    //! アナザーステージ選択背景テクスチャ
    public static final String TEXTURE_ANOTHERSTAGESELECTBACKGROUND = "BackGround/BackStageSelect.png";

    //! ボスステージ選択背景テクスチャ
    public static final String TEXTURE_BOSSSTAGESELECTBACKGROUND = "BackGround/BossStageSelect.png";

    //! ゲーム中断背景テクスチャ
    public static final String TEXTURE_GAMEBREAKBACKGROUND = "BackGround/GameBreakScene.png";

    //! ゲームクリア背景テクスチャ
    public static final String TEXTURE_GAMECLEARBACKGROUND = "BackGround/GameClear.png";

    //! ゲームオーバー背景テクスチャ
    public static final String TEXTURE_GAMEOVERBACKGROUND = "BackGround/GameOver.png";

    //! 憎悪の扉背景テクスチャ
    public static final String TEXTURE_DOORHATREDBACKGROUND = "BackGround/Password.jpg";

    //! 持ち上げられた憎悪の扉背景テクスチャ
    public static final String TEXTURE_DOORHATREDLIFTEDBACKGROUND = "BackGround/PasswordLifted.jpg";

    //! ステージ選択背景テクスチャ
    public static final String TEXTURE_STAGESELECTBACKGROUND = "BackGround/StageSelect.png";

    //! ジョイスティックテクスチャ
    public static final String TEXTURE_JOYSTICK = "Controller/JoyStick.png";

    //! スティックテクスチャ
    public static final String TEXTURE_STICK = "Controller/Stick.png";

    //! 敵テクスチャ：アサシンウルフ
    public static final String TEXTURE_ASSASSINWOLF = "BackEnemy/AssassinWolf.png";

    //! 敵テクスチャ：ファイアバードマン
    public static final String TEXTURE_FIREBIRDMAN = "BackEnemy/FireBirdMan.png";

    //! 敵テクスチャ：黄金戦士
    public static final String TEXTURE_GOLDWARRIOR = "BackEnemy/GoldWarrior.png";

    //! 敵テクスチャ：アイスバードマン
    public static final String TEXTURE_ICEBIRDMAN = "BackEnemy/IceBirdMan.png";

    //! 敵テクスチャ：魔導士
    public static final String TEXTURE_MAGE = "BackEnemy/Mage.png";

    //! 敵テクスチャ：サキュバス
    public static final String TEXTURE_SUCCUBUS = "BackEnemy/Succubus.png";

    //! 敵テクスチャ：戦士
    public static final String TEXTURE_WARRIOR = "BackEnemy/Warrior.png";

    //! 敵テクスチャ：ウルフ
    public static final String TEXTURE_WOLF = "BackEnemy/Wolf.png";

    //! 敵テクスチャ：ウッドバードマン
    public static final String TEXTURE_WOODBIRDMAN = "BackEnemy/WoodBirdMan.png";

    //! 敵フレームテクスチャ
    public static final String TEXTURE_ENEMYFRAME = "UI/EnemyFrame.png";

    //! 召喚パワーテクスチャ
    public static final String TEXTURE_CALLPOWER = "UI/CallPower.png";

    //! 召喚パワーバーテクスチャ
    public static final String TEXTURE_CALLPOWERBER = "UI/CallPowerBer.png";

    //! アナザーステージ1背景テクスチャ
    public static final String TEXTURE_ANOTHERSTAGE1BACKGROUND = "BackStageBg/Stage1Bg.png";

    //! アナザーステージ2背景テクスチャ
    public static final String TEXTURE_ANOTHERSTAGE2BACKGROUND = "BackStageBg/Stage2Bg.jpg";

    //! アナザーステージ3背景テクスチャ
    public static final String TEXTURE_ANOTHERSTAGE3BACKGROUND = "BackStageBg/Stage3Bg.jpg";

    //! アナザー分岐ゴールテクスチャ
    public static final String TEXTURE_ANOTHERBRANCHGOAL = "Item/BackBranchGoal_Item.png";

    //! アナザーのゴールテクスチャ
    public static final String TEXTURE_ANOTHERGOAL = "Item/BackGoal_Item.png";

    //! クリアテクスチャ
    public static final String TEXTURE_CLEAR = "Item/Clear.png";

    //! ゴールドゴールテクスチャ
    public static final String TEXTURE_GOLDGOAL = "Item/Goal_Item1.png";

    //! シルバーゴールテクスチャ
    public static final String TEXTURE_SILVERGOAL = "Item/Goal_Item2.png";

    //! アナザーステージテクスチャ
    public static final String TEXTURE_ANOTHERSTORY = "Menu/AnotherStory.png";

    //! クレジットテクスチャ
    public static final String TEXTURE_CREDIT = "Menu/Credit.png";

    //! 憎悪の扉テクスチャ
    public static final String TEXTURE_DOORHATRED = "Menu/DoorHatred.png";

    //! ステータステクスチャ
    public static final String TEXTURE_STATUS = "Menu/Status.png";

    //! 物語テクスチャ
    public static final String TEXTURE_STORY = "Menu/Story.png";

    //! オプションテクスチャ
    public static final String TEXTURE_OPTION = "Menu/Option.png";

    //! 終了テクスチャ
    public static final String TEXTURE_EXIT = "Menu/Exit.png";

    //! サウンドオンテクスチャ
    public static final String TEXTURE_SOUNDON = "Option/SoundON.png";

    //! サウンドオフテクスチャ
    public static final String TEXTURE_SOUNDOFF = "Option/SoundOFF.png";

    //! 強迫観念テクスチャ
    public static final String TEXTURE_OBSESSION = "Obsession/Obsession.png";

    //! 強迫観念パンプキングテクスチャ
    public static final String TEXTURE_OBSESSIONPUMPKING = "Obsession/PumpKing.png";

    //! 鍵テクスチャ
    public static final String TEXTURE_KEY = "Password/Key.png";

    //! ステージ1背景テクスチャ
    public static final String TEXTURE_STAGE1BACKGROUND = "StageBg/Stage1Bg.jpg";

    //! ステージ2背景テクスチャ
    public static final String TEXTURE_STAGE2BACKGROUND = "StageBg/Stage2Bg.jpg";

    //! ステージ3背景テクスチャ
    public static final String TEXTURE_STAGE3BACKGROUND = "StageBg/Stage3Bg.jpg";

    //! ステージ4背景テクスチャ
    public static final String TEXTURE_STAGE4BACKGROUND = "StageBg/Stage4Bg.jpg";

    //! ステージ5背景テクスチャ
    public static final String TEXTURE_STAGE5BACKGROUND = "StageBg/Stage5Bg.jpg";

    //! ステージ6背景テクスチャ
    public static final String TEXTURE_STAGE6BACKGROUND = "StageBg/Stage6Bg.png";

    //! ファイアボールボタンテクスチャ
    public static final String TEXTURE_FIREBALLBUTTON = "UI/FireBallButton.png";

    //! 召喚ボタンテクスチャ
    public static final String TEXTURE_CALLBUTTON = "UI/CallButton.png";

    //! 自殺ボタンテクスチャ
    public static final String TEXTURE_DEADBUTTON = "UI/DeadButton.png";

    //! ジャンプボタンテクスチャ
    public static final String TEXTURE_JUMPBUTTON = "UI/JumpButton.png";

    //! ステージ1タイトルテクスチャ
    public static final String TEXTURE_STAGE1TITLE = "StageTitle/Stage1Title.png";

    //! ステージ2タイトルテクスチャ
    public static final String TEXTURE_STAGE2TITLE = "StageTitle/Stage2Title.png";

    //! ステージ3タイトルテクスチャ
    public static final String TEXTURE_STAGE3TITLE = "StageTitle/Stage3Title.png";

    //! ステージ4タイトルテクスチャ
    public static final String TEXTURE_STAGE4TITLE = "StageTitle/Stage4Title.png";

    //! ステージ5タイトルテクスチャ
    public static final String TEXTURE_STAGE5TITLE = "StageTitle/Stage5Title.png";

    //! ステージ6タイトルテクスチャ
    public static final String TEXTURE_STAGE6TITLE = "StageTitle/Stage6Title.png";

    //! アナザーステージ1タイトルテクスチャ
    public static final String TEXTURE_ANOTHERSTAGE1TITLE = "StageTitle/AnotherStage1Title.png";

    //! アナザーステージ2タイトルテクスチャ
    public static final String TEXTURE_ANOTHERSTAGE2TITLE = "StageTitle/AnotherStage2Title.png";

    //! アナザーステージ3タイトルテクスチャ
    public static final String TEXTURE_ANOTHERSTAGE3TITLE = "StageTitle/AnotherStage3Title.png";

    //! セレクトボタンNOテクスチャ
    public static final String TEXTURE_SELECTBUTTON_NO = "Pause/SelectButtonNo.png";

    //! セレクトボタンYESテクスチャ
    public static final String TEXTURE_SELECTBUTTON_YES = "Pause/SelectButtonYes.png";

    //! バックボタンテクスチャ
    public static final String TEXTURE_BACKBUTTON = "Pause/BackButton.png";

    //! メニューセレクト背景テクスチャ
    public static final String TEXTURE_MENUSELECTBACK = "Pause/MenuSelectBack.png";

    //! ステージセレクト背景テクスチャ
    public static final String TEXTURE_STAGESELECTBACK = "Pause/StageSelectBack.png";

    /**
     * @brief コンストラクタ
     * @details テクスチャパスリストの初期化を行う
     */
    public TextureInfo(){

        this.pathList = new ArrayList<>();

    }

    /**
     * @brief titleLoad
     * @details タイトル画面で使用するテクスチャパスをリストに追加を行う
     */
    public void titleLoad(){

        this.pathList.add(TEXTURE_PLAYER);

        this.pathList.add(TEXTURE_PUMPKIN);

        this.pathList.add(TEXTURE_WHITEGHOST);

        this.pathList.add(TEXTURE_BLACKGHOST);

        this.pathList.add(TEXTURE_TITLEBACKBACKGROUND);

        this.pathList.add(TEXTURE_STAGECHIP);

        this.pathList.add(TEXTURE_TITLEL);

        this.pathList.add(TEXTURE_TITLETOUCH);

    }

    /**
     * @brief menuLoad
     * @details メニュー画面で使用するテクスチャパスをリストに追加を行う
     */
    public void menuLoad(){

        this.pathList.add(TEXTURE_STORY);

        this.pathList.add(TEXTURE_ANOTHERSTORY);

        this.pathList.add(TEXTURE_DOORHATRED);

        this.pathList.add(TEXTURE_STATUS);

        this.pathList.add(TEXTURE_OPTION);

        this.pathList.add(TEXTURE_CREDIT);

        this.pathList.add(TEXTURE_EXIT);

        this.pathList.add(TEXTURE_STAGE5BACKGROUND);

        this.pathList.add(TEXTURE_ANOTHERSTAGE3BACKGROUND);

        this.pathList.add(TEXTURE_ANOTHERSTAGE1BACKGROUND);

        this.pathList.add(TEXTURE_STAGE1BACKGROUND);

        this.pathList.add(TEXTURE_GAMEBREAKBACKGROUND);

        this.pathList.add(TEXTURE_KEY);

    }

    /**
     * @brief pauseLoad
     * @details ポーズ画面で使用するテクスチャパスをリストに追加を行う
     */
    public void pauseLoad(){

        this.pathList.add(TEXTURE_BACKBUTTON);

        this.pathList.add(TEXTURE_MENUSELECTBACK);

        this.pathList.add(TEXTURE_STAGESELECTBACK);

        this.pathList.add(TEXTURE_SELECTBUTTON_YES);

        this.pathList.add(TEXTURE_SELECTBUTTON_NO);

        this.pathList.add(TEXTURE_GAMEBREAKBACKGROUND);

    }

    /**
     * @brief stageSelectLoad
     * @details ステージ選択画面で使用するテクスチャパスをリストに追加を行う
     */
    public void stageSelectLoad(){

        pauseLoad();

        if(!GameElement.getGameElement().isAnotherStory()){

            this.pathList.add(TEXTURE_STAGESELECTBACKGROUND);

            this.pathList.add(TEXTURE_BOSSSTAGESELECTBACKGROUND);

            this.pathList.add(TEXTURE_PLAYER);

            this.pathList.add(TEXTURE_STAGE1TITLE);

            this.pathList.add(TEXTURE_STAGE2TITLE);

            this.pathList.add(TEXTURE_STAGE3TITLE);

            this.pathList.add(TEXTURE_STAGE4TITLE);

            this.pathList.add(TEXTURE_STAGE5TITLE);

            this.pathList.add(TEXTURE_STAGE6TITLE);


        }else{

            this.pathList.add(TEXTURE_ANOTHERSTAGESELECTBACKGROUND);

            this.pathList.add(TEXTURE_PUMPKING);

            this.pathList.add(TEXTURE_ANOTHERSTAGE1TITLE);

            this.pathList.add(TEXTURE_ANOTHERSTAGE2TITLE);

            this.pathList.add(TEXTURE_ANOTHERSTAGE3TITLE);

        }

    }

    /**
     * @brief doorHatredLoad
     * @details 憎悪の扉画面で使用するテクスチャパスをリストに追加を行う
     */
    public void doorHatredLoad(){

        pauseLoad();

        this.pathList.add(TEXTURE_DOORHATREDBACKGROUND);

        this.pathList.add(TEXTURE_DOORHATREDLIFTEDBACKGROUND);

        this.pathList.add(TEXTURE_KEY);

    }

    /**
     * @brief gameBreakLoad
     * @details ゲーム中断画面で使用するテクスチャパスをリストに追加を行う
     */
    public void gameBreakLoad(){

        this.pathList.add(TEXTURE_GAMEBREAKBACKGROUND);

    }

    /**
     * @brief controllerLoad
     * @details コントローラー画面で使用するテクスチャパスをリストに追加を行う
     */
    public void controllerLoad(){

        this.pathList.add(TEXTURE_JOYSTICK);

        this.pathList.add(TEXTURE_STICK);

        this.pathList.add(TEXTURE_FIREBALLBUTTON);

        this.pathList.add(TEXTURE_CALLBUTTON);

        this.pathList.add(TEXTURE_DEADBUTTON);

        this.pathList.add(TEXTURE_JUMPBUTTON);

    }

    /**
     * @brief callLoad
     * @details 召喚画面で使用するテクスチャパスをリストに追加を行う
     */
    public void callLoad(){

        this.pathList.add(TEXTURE_ENEMYFRAME);

        this.pathList.add(TEXTURE_CALLPOWER);

        this.pathList.add(TEXTURE_CALLPOWERBER);

        this.pathList.add(TEXTURE_PUMPKIN);

        this.pathList.add(TEXTURE_GREENPUMPKIN);

        this.pathList.add(TEXTURE_GHOSTPUMPKIN);

        this.pathList.add(TEXTURE_WHITEGHOST);

        this.pathList.add(TEXTURE_BLACKGHOST);

        this.pathList.add(TEXTURE_KINGGHOST);

    }

    /**
     * @brief gameLoad
     * @details ゲーム画面で使用するテクスチャパスをリストに追加を行う
     */
    public void gameLoad(){

        controllerLoad();

        pauseLoad();

        this.pathList.add(TEXTURE_STAGECHIP);

        int stageNo = GameElement.getGameElement().getSelectStageNo();

        if(GameElement.getGameElement().isAnotherStory()){

            callLoad();

            this.pathList.add(TEXTURE_PUMPKING);

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

            this.pathList.add(TEXTURE_OBSESSION);

            this.pathList.add(TEXTURE_PLAYER);

            this.pathList.add(TEXTURE_FIREBALL);

            this.pathList.add(TEXTURE_GOLDGOAL);

            this.pathList.add(TEXTURE_SILVERGOAL);

            switch (stageNo){

                case GameElement.STAGE1:

                    stage1Load();

                    break;

                case GameElement.STAGE2:

                    stage2Load();

                    break;

                case GameElement.STAGE3:

                    stage3Load();

                    break;

                case GameElement.STAGE4:

                    stage4Load();

                    break;

                case GameElement.STAGE5:

                    stage5Load();

                    break;

                case GameElement.STAGE6:

                    stage6Load();

                    break;

            }

        }

    }

    /**
     * @brief stage1Load
     * @details ステージ1で使用するテクスチャパスをリストに追加を行う
     */
    public void stage1Load(){

        this.pathList.add(TEXTURE_PUMPKIN);

        this.pathList.add(TEXTURE_GREENPUMPKIN);

        this.pathList.add(TEXTURE_WHITEGHOST);

        this.pathList.add(TEXTURE_BLACKGHOST);

        this.pathList.add(TEXTURE_STAGE1BACKGROUND);

    }

    /**
     * @brief stage2Load
     * @details ステージ2で使用するテクスチャパスをリストに追加を行う
     */
    public void stage2Load(){

        this.pathList.add(TEXTURE_PUMPKIN);

        this.pathList.add(TEXTURE_GREENPUMPKIN);

        this.pathList.add(TEXTURE_WHITEGHOST);

        this.pathList.add(TEXTURE_KINGGHOST);

        this.pathList.add(TEXTURE_STAGE2BACKGROUND);

    }

    /**
     * @brief stage3Load
     * @details ステージ3で使用するテクスチャパスをリストに追加を行う
     */
    public void stage3Load(){

        this.pathList.add(TEXTURE_PUMPKIN);

        this.pathList.add(TEXTURE_GREENPUMPKIN);

        this.pathList.add(TEXTURE_GHOSTPUMPKIN);

        this.pathList.add(TEXTURE_WHITEGHOST);

        this.pathList.add(TEXTURE_BLACKGHOST);

        this.pathList.add(TEXTURE_KINGGHOST);

        this.pathList.add(TEXTURE_STAGE3BACKGROUND);

    }

    /**
     * @brief stage4Load
     * @details ステージ4で使用するテクスチャパスをリストに追加を行う
     */
    public void stage4Load(){

        this.pathList.add(TEXTURE_PUMPKIN);

        this.pathList.add(TEXTURE_GREENPUMPKIN);

        this.pathList.add(TEXTURE_GHOSTPUMPKIN);

        this.pathList.add(TEXTURE_WHITEGHOST);

        this.pathList.add(TEXTURE_BLACKGHOST);

        this.pathList.add(TEXTURE_KINGGHOST);

        this.pathList.add(TEXTURE_STAGE4BACKGROUND);

    }

    /**
     * @brief stage5Load
     * @details ステージ5で使用するテクスチャパスをリストに追加を行う
     */
    public void stage5Load(){

        this.pathList.add(TEXTURE_PUMPKIN);

        this.pathList.add(TEXTURE_GREENPUMPKIN);

        this.pathList.add(TEXTURE_GHOSTPUMPKIN);

        this.pathList.add(TEXTURE_WHITEGHOST);

        this.pathList.add(TEXTURE_BLACKGHOST);

        this.pathList.add(TEXTURE_KINGGHOST);

        this.pathList.add(TEXTURE_STAGE5BACKGROUND);

    }

    /**
     * @brief stage6Load
     * @details ステージ6で使用するテクスチャパスをリストに追加を行う
     */
    public void stage6Load(){

        this.pathList.add(TEXTURE_PUMPKING);

        this.pathList.add(TEXTURE_STAGECHIP);

        this.pathList.add(TEXTURE_STAGE6BACKGROUND);

        this.pathList.add(TEXTURE_CLEAR);

    }

    /**
     * @brief anotherStage1Load
     * @details アナザーステージ1で使用するテクスチャパスをリストに追加を行う
     */
    public void anotherStage1Load(){

        this.pathList.add(TEXTURE_FIREBIRDMAN);

        this.pathList.add(TEXTURE_ICEBIRDMAN);

        this.pathList.add(TEXTURE_WOODBIRDMAN);

        this.pathList.add(TEXTURE_WARRIOR);

        this.pathList.add(TEXTURE_GOLDWARRIOR);

        this.pathList.add(TEXTURE_SUCCUBUS);

        this.pathList.add(TEXTURE_MAGE);

        this.pathList.add(TEXTURE_WOLF);

        this.pathList.add(TEXTURE_ANOTHERGOAL);

        this.pathList.add(TEXTURE_ANOTHERBRANCHGOAL);

        this.pathList.add(TEXTURE_ANOTHERSTAGE1BACKGROUND);

    }

    /**
     * @brief anotherStage2Load
     * @details アナザーステージ2で使用するテクスチャパスをリストに追加を行う
     */
    public void anotherStage2Load(){

        this.pathList.add(TEXTURE_ASSASSINWOLF);

        this.pathList.add(TEXTURE_ANOTHERGOAL);

        this.pathList.add(TEXTURE_ANOTHERSTAGE2BACKGROUND);

    }

    /**
     * @brief anotherStage3Load
     * @details アナザーステージ3で使用するテクスチャパスをリストに追加を行う
     */
    public void anotherStage3Load(){

        this.pathList.add(TEXTURE_OBSESSION);

        this.pathList.add(TEXTURE_ANOTHERSTAGE3BACKGROUND);

    }

    /**
     * @brief gameOverLoad
     * @details ゲームオーバー画面で使用するテクスチャパスをリストに追加を行う
     */
    public void gameOverLoad(){

        this.pathList.add(TEXTURE_GAMEOVERBACKGROUND);

    }

    /**
     * @brief optionLoad
     * @details オプション画面で使用するテクスチャパスをリストに追加を行う
     */
    public void optionLoad(){

        pauseLoad();

        this.pathList.add(TEXTURE_ANOTHERSTAGE1BACKGROUND);

        this.pathList.add(TEXTURE_KEY);

        this.pathList.add(TEXTURE_SOUNDON);

        this.pathList.add(TEXTURE_SOUNDOFF);

    }

    /**
     * @brief fameScoreLoad
     * @details ハイスコア画面で使用するテクスチャパスをリストに追加を行う
     */
    public void fameScoreLoad(){

        pauseLoad();

        this.pathList.add(TEXTURE_STAGE1BACKGROUND);

        this.pathList.add(TEXTURE_STAGE2BACKGROUND);

        this.pathList.add(TEXTURE_STAGE3BACKGROUND);

        this.pathList.add(TEXTURE_STAGE4BACKGROUND);

        this.pathList.add(TEXTURE_STAGE5BACKGROUND);

        this.pathList.add(TEXTURE_STAGE6BACKGROUND);

    }

    /**
     * @brief creditLoad
     * @details クレジット画面で使用するテクスチャパスをリストに追加を行う
     */
    public void creditLoad(){

        pauseLoad();

        this.pathList.add(TEXTURE_STAGE1BACKGROUND);

        this.pathList.add(TEXTURE_STAGE2BACKGROUND);

        this.pathList.add(TEXTURE_STAGE3BACKGROUND);

        this.pathList.add(TEXTURE_STAGE4BACKGROUND);

        this.pathList.add(TEXTURE_STAGE5BACKGROUND);

        this.pathList.add(TEXTURE_STAGE6BACKGROUND);

        this.pathList.add(TEXTURE_ANOTHERSTAGE1BACKGROUND);

        this.pathList.add(TEXTURE_ANOTHERSTAGE3BACKGROUND);

        this.pathList.add(TEXTURE_PLAYER);

        this.pathList.add(TEXTURE_PUMPKING);

        this.pathList.add(TEXTURE_OBSESSIONPUMPKING);

        this.pathList.add(TEXTURE_PUMPKIN);

        this.pathList.add(TEXTURE_GREENPUMPKIN);

        this.pathList.add(TEXTURE_GHOSTPUMPKIN);

        this.pathList.add(TEXTURE_WHITEGHOST);

        this.pathList.add(TEXTURE_BLACKGHOST);

        this.pathList.add(TEXTURE_KINGGHOST);

        this.pathList.add(TEXTURE_STAGECHIP);

    }

}
