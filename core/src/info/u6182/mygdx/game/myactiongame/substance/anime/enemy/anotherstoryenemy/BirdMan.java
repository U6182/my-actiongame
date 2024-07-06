/**
 * @file BirdMan.java
 * @brief ゲーム内のバードマンクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.substance.anime.enemy.anotherstoryenemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.mygdx.game.myactiongame.substance.anime.enemy.Enemy;

import info.u6182.api.animation.AnimeData;
import info.u6182.api.animation.AnimeMotion;
import info.u6182.api.primitive.CustomRectangle;

/**
 * @class BirdMan
 * @brief ゲーム内のバードマンクラス
 */
public class BirdMan extends Enemy {

    //! 移動範囲
    private float rangeY;

    /**
     * @brief コンストラクタ
     * @param texture テクスチャ
     * @param pos 出現座標
     * @param type 種類
     * @param idNo 個体識別番号
     */
    public BirdMan(Texture texture, Vector2 pos, int type, int idNo){

        super(pos, type);

        //サイズの初期化
        this.size = new Vector2(50f, 43f);

        //移動量の初期化
        this.move = new Vector2(-ENEMY_SPEED, -ENEMY_SPEED);

        //個体識別番号の設定
        this.idNo = idNo;

        //移動範囲の初期化
        this.rangeY = 200f;

        AnimeData[] animeData = {new AnimeData(texture, "移動", this.size, true, new int[][]{{0,0},{1,0},{2,0}},0,0,0.08f)};

        //アニメーションの初期化
        this.anime = new AnimeMotion(animeData);

    }

    /**
     * @brief 敵の行動処理を行う
     */
    @Override
    public void behavior(){

        if(this.pos.y < this.startPos.y - this.rangeY){

            this.move.y = -this.move.y;

        }

        this.pos.add(this.move);

    }

    /**
     * @brief ステージとの衝突処理を行う
     * @param buried 埋まり値
     */
    @Override
    public void collisionStage(Vector2 buried){
        ;
    }

    /**
     * @brief 矩形の取得を行う
     * @return 矩形を返す
     */
    @Override
    public CustomRectangle getRect(){

        return this.rect.setCustomRect(this.pos.x + 10f, this.pos.y, this.pos.x + this.size.x - 7f, this.pos.y + this.size.y);

    }

}
