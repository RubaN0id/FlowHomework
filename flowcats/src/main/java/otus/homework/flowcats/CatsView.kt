package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun load() {
        findViewById<TextView>(R.id.fact_textView).text = context.getText(R.string.loading)
    }

    override fun populate(fact: Fact) {
        findViewById<TextView>(R.id.fact_textView).text = fact.fact
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message ?: context.getText(R.string.error), Toast.LENGTH_LONG)
            .show()
    }
}

interface ICatsView {

    fun load()
    fun populate(fact: Fact)
    fun showError(message: String?)
}