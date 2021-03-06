package generator

import com.intellij.notification.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.Messages

class MessageDelegate {
    companion object {
        private const val GROUP_LOG = "JSON2Ts_GENERATOR_LOG"
    }

    private val logGroup =
        NotificationGroup(GROUP_LOG, NotificationDisplayType.NONE, true)

    fun onException(throwable: Throwable) {
        val message = throwable.message ?: "Something went wrong"
        sendNotification(
            logGroup.createNotification(message , NotificationType.INFORMATION)
        )
        showMessage(message, "Error")
    }

    fun showMessage(message: String) {
        showMessage(message, "")
    }

    fun log(message: String) {
        sendNotification(
            logGroup.createNotification(message, NotificationType.INFORMATION)
        )
    }

    private fun sendNotification(notification: Notification) {
        ApplicationManager.getApplication().invokeLater {
            val projects = ProjectManager.getInstance().openProjects
            Notifications.Bus.notify(notification, projects[0])
        }
    }

    private fun showMessage(message: String, header: String) {
        ApplicationManager.getApplication().invokeLater {
            Messages.showDialog(message, header, arrayOf("OK"), -1, null)
        }
    }
}