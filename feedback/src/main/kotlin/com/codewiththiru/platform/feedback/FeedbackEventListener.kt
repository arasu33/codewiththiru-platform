package com.codewiththiru.platform.feedback

import com.codewiththiru.platform.feedback.model.AttachmentType
import com.codewiththiru.platform.feedback.model.FeedbackCategory
import com.codewiththiru.platform.feedback.model.FeedbackSubmissionResult

interface FeedbackEventListener {
    fun onFeedbackOpened()

    fun onFeedbackSubmitted(category: FeedbackCategory)

    fun onAttachmentAdded(type: AttachmentType)

    fun onAttachmentRemoved(type: AttachmentType)

    fun onDraftSaved()

    fun onDraftRestored()

    fun onSubmissionError(error: FeedbackSubmissionResult)
}
