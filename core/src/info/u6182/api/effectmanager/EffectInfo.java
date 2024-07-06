/**
 * @file EffectInfo.java
 * @brief エフェクト情報を管理するクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.effectmanager;

import info.u6182.api.AssetInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;

import java.util.ArrayList;

/**
 * @class EffectInfo
 * @brief エフェクト情報を管理するクラス
 */
public class EffectInfo extends AssetInfo {

    //! 召喚エフェクト
    public static final String EFFECT_CALL = "Effect/Call.png";

    //! 呪い状態エフェクト
    public static final String EFFECT_CURSESTATE = "Effect/CurseState.png";

    //! 敗北エフェクト
    public static final String EFFECT_DEFEAT = "Effect/Defeat.png";

    //! 非道の王の爆発エフェクト
    public static final String EFFECT_HELLISHESTKING_EXPLOSION = "Effect/HellishestKing_Explosion.png";

    //! 非道の王の爆流壁エフェクト
    public static final String EFFECT_HELLISHESTKING_EXPLOSIONWALL = "Effect/HellishestKing_ExplosionWall.png";

    //! 非道の王の氷魔法エフェクト
    public static final String EFFECT_HELLISHESTKING_ICEMAGIC = "Effect/HellishestKing_IceMagic.png";

    //! 非道の王の岩流壁エフェクト
    public static final String EFFECT_HELLISHESTKING_ROCKWALL = "Effect/HellishestKing_RockWall.png";

    //! 非道の王の雷魔法エフェクト
    public static final String EFFECT_HELLISHESTKING_THUNDERMAGIC = "Effect/HellishestKing_ThunderMagic.png";

    //! 非道の王のかまいたちエフェクト
    public static final String EFFECT_HELLISHESTKING_WINDBEAST = "Effect/HellishestKing_WindBeast.png";

    //! 非道の王の風魔法エフェクト
    public static final String EFFECT_HELLISHESTKING_WINDMAGIC = "Effect/HellishestKing_WindMagic.png";

    //! 非道の王の魔法エフェクト
    public static final String EFFECT_HELLISHESTKING_MAGIC = "Effect/HellishestKing_Magic.png";

    //! 憎しみの扉解除エフェクト
    public static final String EFFECT_LIFTED = "Effect/Lifted.png";

    //! 魔導士の呪いエフェクト
    public static final String EFFECT_MAGECURSE = "Effect/MageCurse.png";

    //! 魔導士の氷流壁エフェクト
    public static final String EFFECT_MAGEICEWALL = "Effect/MageIceWall.png";

    //! 魔導士の幻覚エフェクト
    public static final String EFFECT_MAGETEMPTATION = "Effect/MageTemptation.png";

    //! 魔導士の雷流壁エフェクト
    public static final String EFFECT_MAGETHUNDERWALL = "Effect/MageThunderWall.png";

    //! 岩流壁エフェクト
    public static final String EFFECT_ROCKWALL = "Effect/RockWall.png";

    //! 誘惑エフェクト
    public static final String EFFECT_TEMPTATION = "Effect/Temptation.png";

    //! 誘惑状態エフェクト
    public static final String EFFECT_TEMPTATIONSTATE = "Effect/TemptationState.png";

    //! 戦士の攻撃エフェクト
    public static final String EFFECT_WARRIORATTACK = "Effect/WarriorAttack.png";

    /**
     * @brief コンストラクタ
     */
    public EffectInfo(){

        this.pathList = new ArrayList<>();

    }

    /**
     * @brief 憎しみの扉解除エフェクトの読み込みを行う
     */
    public void doorHatredLoad(){

        this.pathList.add(EFFECT_LIFTED);

    }

    /**
     * @brief ゲームエフェクトの読み込みを行う
     */
    public void gameLoad(){

        int stageNo = GameElement.getGameElement().getSelectStageNo();

        if(GameElement.getGameElement().isAnotherStory()){

            this.pathList.add(EFFECT_CALL);

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

            this.pathList.add(EFFECT_DEFEAT);

        }

    }

    /**
     * @brief アナザーストーリーステージ1のエフェクトの読み込みを行う
     */
    public void anotherStage1Load(){

        this.pathList.add(EFFECT_MAGECURSE);

        this.pathList.add(EFFECT_MAGETEMPTATION);

        this.pathList.add(EFFECT_MAGEICEWALL);

        this.pathList.add(EFFECT_MAGETHUNDERWALL);

        this.pathList.add(EFFECT_ROCKWALL);

        this.pathList.add(EFFECT_WARRIORATTACK);

        this.pathList.add(EFFECT_TEMPTATION);

        this.pathList.add(EFFECT_CURSESTATE);

        this.pathList.add(EFFECT_TEMPTATIONSTATE);


    }

    /**
     * @brief アナザーストーリーステージ2のエフェクトの読み込みを行う
     */
    public void anotherStage2Load(){

        this.pathList.add(EFFECT_WARRIORATTACK);

    }

    /**
     * @brief アナザーストーリーステージ3のエフェクトの読み込みを行う
     */
    public void anotherStage3Load(){

        this.pathList.add(EFFECT_MAGETHUNDERWALL);

        this.pathList.add(EFFECT_MAGEICEWALL);

        this.pathList.add(EFFECT_ROCKWALL);

        this.pathList.add(EFFECT_HELLISHESTKING_ROCKWALL);

        this.pathList.add(EFFECT_HELLISHESTKING_EXPLOSIONWALL);

        this.pathList.add(EFFECT_HELLISHESTKING_WINDBEAST);

        this.pathList.add(EFFECT_HELLISHESTKING_EXPLOSION);

        this.pathList.add(EFFECT_HELLISHESTKING_THUNDERMAGIC);

        this.pathList.add(EFFECT_HELLISHESTKING_ICEMAGIC);

        this.pathList.add(EFFECT_HELLISHESTKING_WINDMAGIC);

        this.pathList.add(EFFECT_HELLISHESTKING_MAGIC);

    }

}
