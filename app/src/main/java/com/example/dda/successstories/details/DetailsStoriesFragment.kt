package com.example.dda.successstories.details

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.TypedValue
import android.view.*
import android.widget.SeekBar
import com.example.dda.successstories.MyPreference
import com.example.dda.successstories.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_dialog_with_seek.view.*
import kotlinx.android.synthetic.main.details_stories_fragment.*

val myPreference: MyPreference by lazy {
    DetailsStoriesFragment.myPreference!!
}

class DetailsStoriesFragment : Fragment() {

    companion object {
        var myPreference: MyPreference? = null
    }

    private lateinit var viewModel: DetailsStoriesViewModel
    private var spTextSize: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.details_stories_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.editSize -> {
                alertWithSeek()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("InflateParams")
    private fun alertWithSeek() {

        var pr = 0

        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_with_seek, null)

        val builder = this.context?.let { AlertDialog.Builder(it) }

        builder?.setView(dialogLayout)
        builder?.setTitle("Размер текста")
        builder?.setPositiveButton("OK", { _: DialogInterface, _: Int ->
            myPreference?.setTextSize(pr)
        })
        builder?.show()

        if (spTextSize != -1) {
            dialogLayout.seek_bar.progress = this.spTextSize
        }

        dialogLayout.seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                det_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, progress+12f)
                pr = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailsStoriesViewModel::class.java)
        // TODO: Use the ViewModel


        myPreference = MyPreference(requireContext())
        spTextSize = myPreference!!.getTextSize()

        det_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, spTextSize + 12f)

        val content = arguments?.getString("content")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            det_content.text = android.text.Html.fromHtml(content, android.text.Html.FROM_HTML_MODE_COMPACT)
        } else {
            det_content.text = android.text.Html.fromHtml(content)
        }

        det_title.text = arguments?.getString("title")
        Picasso.get()
            .load(arguments?.getString("imageUrl"))
            .into(det_main_photo)

    }



}


