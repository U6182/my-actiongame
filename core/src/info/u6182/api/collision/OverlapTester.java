/**
 * @file OverlapTester.java
 * @brief 衝突判定を行うクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */

package info.u6182.api.collision;

import info.u6182.api.primitive.Circle;
import info.u6182.api.primitive.Rectangle;
import info.u6182.api.vector.Vector2;

/**
 * @class OverlapTester
 * @brief 衝突判定を行うクラス
 */
public class OverlapTester {

    /**
     * @brief overlapCircles
     * @param c1 円1
     * @param c2 円2
     * @return boolean true 円1と円2が衝突した(重なった) false 円1と円2が衝突していない(重なっていない)
     * @details 円と円の衝突結果の取得を行う
     */
    public static boolean overlapCircles(Circle c1, Circle c2){

        //二つの円の中心の距離
        float distance = c1.center.dist(c2.center);

        float radiusSum = c1.radius + c2.radius;

        //中心の距離が二つの円の合計以下の場合衝突している
        return distance <= radiusSum * radiusSum;

    }

    /**
     * @brief overlapRectangles
     * @param r1 矩形1
     * @param r2 矩形2
     * @return boolean true 矩形1と矩形2が衝突した(重なった) false 矩形1と矩形2が衝突していない(重なっていない)
     * @details 矩形と矩形の衝突結果の取得を行う
     */
    public static boolean overlapRectangles(Rectangle r1, Rectangle r2){

        if(r1.lowerLeft.x < r2.lowerLeft.x + r2.width &&
                r1.lowerLeft.x + r1.width > r2.lowerLeft.x &&
                r1.lowerLeft.y < r2.lowerLeft.y + r2.height &&
                r1.lowerLeft.y + r1.height > r2.lowerLeft.y){

            return true;

        }else{

            return false;

        }

    }

    /**
     * @brief overlapCircleRectangle
     * @param c 円
     * @param r 矩形
     * @return boolean true 円と矩形が衝突した(重なった) false 円と矩形が衝突していない(重なっていない)
     * @details 円と矩形の衝突結果の取得を行う
     */
    public static boolean overlapCircleRectangle(Circle c, Rectangle r){

        float closestX = c.center.x;
        float closestY = c.center.y;

        //四角形の上で最も近いポイントを割り出す
        if(c.center.x < r.lowerLeft.x){

            closestX = r.lowerLeft.x;

        }else if(c.center.x > r.lowerLeft.x + r.width){

            closestX = r.lowerLeft.x + r.width;

        }

        if(c.center.y <  r.lowerLeft.y){

            closestY = r.lowerLeft.y;

        }else if(c.center.y > r.lowerLeft.y + r.height){

            closestY = r.lowerLeft.y + r.height;

        }

        //ポイントが円の中にある場合円と四角形は衝突している
        return c.center.distSquared(closestX, closestY) < c.radius * c.radius;

    }

    /**
     * @brief pointInCircle
     * @param c 円
     * @param x X座標
     * @param y Y座標
     * @return boolean true 円とポイントが衝突した(重なった) false 円とポイントが衝突していない(重なっていない)
     * @details 円とポイントの衝突結果の取得を行う
     */
    public static boolean pointInCircle(Circle c, float x, float y){

        //ポイントが円の中にあるか
        return c.center.distSquared(x, y) < c.radius * c.radius;

    }

    /**
     * @brief pointInCircle
     * @param c 円
     * @param p ポイント
     * @return boolean true 円とポイントが衝突した(重なった) false 円とポイントが衝突していない(重なっていない)
     * @details 円とポイントの衝突結果の取得を行う
     */
    public static boolean pointInCircle(Circle c, Vector2 p){

        //ポイントが円の中にあるか
        return c.center.distSquared(p) < c.radius * c.radius;

    }

    /**
     * @brief pointInRectangle
     * @param r 矩形
     * @param x X座標
     * @param y Y座標
     * @return boolean true 矩形とポイントが衝突した(重なった) false 矩形とポイントが衝突していない(重なっていない)
     * @details 矩形とポイントの衝突結果の取得を行う
     */
    public static boolean pointInRectangle(Rectangle r, float x, float y){

        //ポイントが四角形の中にあるか
        return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x && r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;

    }

    /**
     * @brief pointInRectangle
     * @param r 矩形
     * @param p ポイント
     * @return boolean true 矩形とポイントが衝突した(重なった) false 矩形とポイントが衝突していない(重なっていない)
     * @details 矩形とポイントの衝突結果の取得を行う
     */
    public static boolean pointInRectangle(Rectangle r, Vector2 p){

        //ポイントが四角形の中にあるか
        return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x && r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;

    }

}