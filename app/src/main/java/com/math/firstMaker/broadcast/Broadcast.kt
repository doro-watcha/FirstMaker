package com.math.firstMaker.broadcast

import com.math.firstMaker.api.ScoringResponse
import com.math.firstMaker.model.*
import io.reactivex.subjects.PublishSubject
import java.util.*

object Broadcast {

    val makeCollectionRefresh : PublishSubject<Unit> = PublishSubject.create()

    val datePickBroadcast : PublishSubject<Pair<Calendar?, Calendar?>> = PublishSubject.create()

    val COURSE_PICK_BROADCAST : PublishSubject<Subject> = PublishSubject.create()

    val submitNotNullBroadcast : PublishSubject<Int> = PublishSubject.create()

    val submitNullBroadcast : PublishSubject<Int> = PublishSubject.create()

    val pickSmallChapterBroadcast : PublishSubject<List<SmallChapter>> = PublishSubject.create()

    val clickSmallChapterBroadcast : PublishSubject<SmallChapter> = PublishSubject.create()

    val clickMiddleChapterBroadcast : PublishSubject<MiddleChapter> = PublishSubject.create()

    val deleteSmallChapterBroadcast : PublishSubject<SmallChapter> = PublishSubject.create()

    val paperCreatedBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val scoringCompletedBroadcast : PublishSubject<ScoringResponse> = PublishSubject.create()

    /**
     * Reselect
     */
    val homeReselectBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val wrongReselectBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val makeCollectionReselectBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val problemChangeBroadcast : PublishSubject<Triple<Problem,Problem,ProblemSet>> = PublishSubject.create()



    val saveCollectionCompleted : PublishSubject<Unit> = PublishSubject.create()

    val exitApplication : PublishSubject<Unit> = PublishSubject.create()

    val classCreateBroadcast : PublishSubject<Unit> = PublishSubject.create()


    val moveToFirstPageBroadcast  : PublishSubject<Int> = PublishSubject.create()

    val moveToHomeBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val sourcePickBroadcast : PublishSubject<Source> = PublishSubject.create()

}