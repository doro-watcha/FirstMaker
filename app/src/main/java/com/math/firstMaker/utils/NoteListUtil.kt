package com.math.firstMaker.utils

import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.model.Note
import com.math.firstMaker.repository.ExamRepository
import com.math.firstMaker.repository.HomeworkRepository
import com.math.firstMaker.repository.WorkPaperRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 1/11/21
 */

class NoteListUtil (
    val workPaperRepository: WorkPaperRepository,
    val homeworkRepository: HomeworkRepository,
    val examRepository: ExamRepository
) {


}