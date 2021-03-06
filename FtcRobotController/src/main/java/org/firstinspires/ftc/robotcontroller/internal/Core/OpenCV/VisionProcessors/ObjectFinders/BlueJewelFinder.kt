@file:Suppress("PackageDirectoryMismatch")
package org.directcurrent.opencv.visionprocessors.objectfinders


import org.directcurrent.opencv.BlueJewelLower
import org.directcurrent.opencv.BlueJewelUpper
import org.directcurrent.opencv.CVBridge
import org.firstinspires.ftc.robotcontroller.internal.Core.Utility.CPPoint
import org.opencv.core.Core
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc


/**
 * In charge of detecting and reporting the Blue Jewel
 */
class BlueJewelFinder: ObjectFinder(BlueJewelLower , BlueJewelUpper)
{
    /**
     * Displays information of any detected Blue Jewels- draws a contour around them and
     * gives an x and y position
     */
    override fun displayInfo(contours: ArrayList<MatOfPoint>)
    {
        CVBridge.blueJewelPoints.clear()

        for(i in contours)
        {
            val rect = Imgproc.boundingRect(i)

            // If it's roughly in the shape of a square and large enough
            if(rect.height >= 30 && rect.width >= 30)
            {
                // If it's below a certain line (on the floor-ish)
                if(rect.x <= 200 && rect.area() < 50_000)
                {
                    Imgproc.rectangle(displayMat, Point(rect.x.toDouble() , rect.y.toDouble()) ,
                            Point((rect.x + rect.width).toDouble() , (rect.y + rect.height).toDouble()) ,
                            Scalar(0.0 , 0.0 , 255.0) , 6)

                    Imgproc.putText(displayMat, "Blue Jewel :)"
                            , Point(rect.x.toDouble() ,
                            (rect.y + rect.height + 50).toDouble()) , Core.FONT_HERSHEY_COMPLEX , 1.0 ,
                            Scalar(0.0 , 255.0 , 0.0) , 3)

                    Imgproc.putText(displayMat, "x: " + rect.x , Point(rect.x.toDouble() ,
                            (rect.y + rect.height + 90).toDouble()) , Core.FONT_HERSHEY_COMPLEX , 1.0 ,
                            Scalar(0.0 , 255.0 , 0.0) , 3)

                    Imgproc.putText(displayMat, "y: " + rect.y , Point(rect.x.toDouble() ,
                            (rect.y + rect.height + 130).toDouble()) , Core.FONT_HERSHEY_COMPLEX , 1.0 ,
                            Scalar(0.0 , 255.0 , 0.0) , 3)

                    CVBridge.blueJewelPoints.add(CPPoint(rect.x.toDouble(), rect.y.toDouble(), CPPoint.Type.CARTESIAN))
                }
            }
        }
    }
}