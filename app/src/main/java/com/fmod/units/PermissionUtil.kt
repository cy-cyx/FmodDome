package com.fmod.units

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fmod.R

object PermissionUtil {
    private var sPermissionCallback: IPermissionCallback? = null
    private var sPermissions: Array<String>? = null

    private const val sCodeRequestPermission = 9999
    private const val sCodeGotoSetPage = 9998

    fun requestRuntimePermissions(
        context: Context?,
        permissions: Array<String>?,
        permissionCallback: IPermissionCallback?
    ) {
        if (context == null || permissions == null) {
            return
        }
        sPermissionCallback = permissionCallback
        sPermissions = permissions
        requestPermissions(context, sPermissions)
    }

    private fun requestPermissions(context: Context, permissions: Array<String>?) {
        val permissionList: MutableList<String> = ArrayList()
        for (permission in permissions!!) {
            if (ContextCompat.checkSelfPermission(context, permission) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.add(permission)
            }
        }
        // 申请权限
        if (permissionList.isNotEmpty()) {

            // 检查有没有拒接的权限
            val refusePermissionList: MutableList<String> = ArrayList()
            for (i in permissionList.indices) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        (context as Activity),
                        permissionList[i]
                    )
                ) {
                    refusePermissionList.add(permissionList[i])
                }
            }

            if (refusePermissionList.isNotEmpty()
                && isHasRequest(refusePermissionList)
            ) {
                val dialog = AlertDialog.Builder(context).apply {
                    setTitle(R.string.permission_refuse_tip)
                    setPositiveButton(
                        R.string.goto_set,
                        object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                gotoSetPage(context)
                            }
                        })
                }
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(
                    (context as Activity),
                    permissionList.toTypedArray(), sCodeRequestPermission
                )
            }

        } else {
            // 拥有权限
            if (sPermissionCallback != null) {
                sPermissionCallback!!.nextStep()
            }
            sPermissionCallback = null
            sPermissions = null
        }
    }

    /**
     * 由于第一次都是拒接不在请求 所有有这样判断
     */
    private fun isHasRequest(permissions: MutableList<String>): Boolean {
        // 存在没有请求过的，就算没有请求过
        var has = true
        permissions.forEach {
            if (!"${MMKVKeys.keyFirstCheckPermission}${it}".getMMKVBool()) {
                has = false
                "${MMKVKeys.keyFirstCheckPermission}${it}".putMMKVBool(true)
            }
        }
        return has
    }

    fun onRequestPermissionsResult(
        context: Context,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray?
    ) {
        if (requestCode == sCodeRequestPermission) {
            if (sPermissions != null) {
                requestPermissions(context, sPermissions)
            }
        }
    }

    fun gotoSetPage(context: Context) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", context.applicationContext.packageName, null)
        (context as Activity).startActivityForResult(intent, sCodeGotoSetPage)
    }

    fun onActivityResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == sCodeGotoSetPage) {
            if (sPermissions != null) {
                requestPermissions(context, sPermissions)
            }
        }
    }

    interface IPermissionCallback {
        fun nextStep()
    }
}