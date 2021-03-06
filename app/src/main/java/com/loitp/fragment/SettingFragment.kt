package com.loitp.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.utilities.LActivityUtil
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import com.loitp.R
import com.loitp.activity.MainActivity
import kotlinx.android.synthetic.main.frm_home.*
import kotlinx.android.synthetic.main.frm_setting.*

@LogTag("SettingFragment")
class SettingFragment : BaseFragment() {
    private var dialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()
    }

    override fun onDestroyView() {
        dialog?.dismiss()
        super.onDestroyView()
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_setting
    }

    private fun setupViews() {
        val isDarkTheme = LUIUtil.isDarkTheme()
        swEnableDarkMode.isChecked = isDarkTheme

        swEnableDarkMode.setOnCheckedChangeListener { _, isChecked ->
            handleSwitchDarkTheme(isChecked = isChecked)
        }
    }

    private fun setupViewModels() {
    }

    private fun handleSwitchDarkTheme(isChecked: Boolean) {
        context?.let { c ->
            val isDarkTheme = LUIUtil.isDarkTheme()
            if (isDarkTheme == isChecked) {
                return@let
            }

            dialog = LDialogUtil.showDialog2(
                context = c,
                title = getString(com.R.string.warning),
                msg = getString(R.string.this_app_will_be_restarted),
                button1 = getString(com.R.string.cancel),
                button2 = getString(com.R.string.ok),
                onClickButton1 = {
                    swEnableDarkMode?.isChecked = LUIUtil.isDarkTheme()
                },
                onClickButton2 = {
                    if (isChecked) {
                        LUIUtil.setDarkTheme(isDarkTheme = true)
                    } else {
                        LUIUtil.setDarkTheme(isDarkTheme = false)
                    }

                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    context?.let {
                        LActivityUtil.transActivityNoAnimation(it)
                    }

                    dialog?.dismiss()
                }
            )
            dialog?.setOnCancelListener {
                swEnableDarkMode?.isChecked = LUIUtil.isDarkTheme()
            }
        }
    }
}
