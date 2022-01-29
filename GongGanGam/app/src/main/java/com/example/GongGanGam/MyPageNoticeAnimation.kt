import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import com.example.GongGanGam.R
import com.example.GongGanGam.databinding.ItemMypageNoticeListBinding

class MyPageNoticeAnimation {
    companion object {

        fun expandLayout(view: View, isExpanded: Boolean): Boolean {
            if (isExpanded) {
                return true
            } else {
                return false
            }
        }

        fun expand(view: View) {
            val animation = expandAction(view)
            view.startAnimation(animation)
        }

        private fun expandAction(view: View) : Animation {
            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val actualHeight = view.measuredHeight

            view.layoutParams.height = 0
            view.visibility = View.VISIBLE

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    view.layoutParams.height = if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
                    else (actualHeight * interpolatedTime).toInt()

                    view.requestLayout()
                }
            }

            animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()

            view.startAnimation(animation)

            return animation
        }

        fun nonexpand(view: View) {
            val actualHeight = view.measuredHeight

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1f) {
                        view.visibility = View.GONE
                    } else {
                        view.layoutParams.height = (actualHeight - (actualHeight * interpolatedTime)).toInt()
                        view.requestLayout()
                    }
                }
            }

            animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
            view.startAnimation(animation)
        }
    }

}