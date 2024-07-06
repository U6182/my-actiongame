/**
 * @file Circle.java
 * @brief 円を表すクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.primitive;

import info.u6182.api.vector.Vector2;

/**
 * @class Circle
 * @brief 円を表すクラス
 */
public class Circle {

    //! 中心
    public final Vector2 center = new Vector2();

    //! 半径
    public float radius;

    /**
     * @brief コンストラクタ
     * @param x 中心のX座標
     * @param y 中心のY座標
     * @param radius 半径
     * @details 円の初期化を行う
     */
    public Circle(float x, float y, float radius){

        this.center.set(x, y);

        this.radius = radius;

    }

    /**
     * @brief overlapCircles
     * @param c1 円1
     * @param c2 円2
     * @return boolean 衝突しているかどうか
     * @details 二つの円の衝突判定を行う
     */
    public boolean overlapCircles(Circle c1, Circle c2){

        //二つの円の中心の距離
        float distance = c1.center.dist(c2.center);

        float radiusSum = c1.radius + c2.radius;

        //中心の距離が二つの円の合計以下の場合衝突している
        return distance <= radiusSum * radiusSum;

    }

}
