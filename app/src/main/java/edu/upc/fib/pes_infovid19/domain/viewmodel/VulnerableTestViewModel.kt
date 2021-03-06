package edu.upc.fib.pes_infovid19.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import edu.upc.fib.pes_infovid19.data.VulnerableTestFirebaseRepository
import edu.upc.fib.pes_infovid19.domain.repository.VulnerableTestRepository
import edu.upc.fib.pes_infovid19.domain.structures.QuestionVulnerabilityTest
import kotlin.math.absoluteValue

class VulnerableTestViewModel : ViewModel() {
    private val repository: VulnerableTestRepository = VulnerableTestFirebaseRepository()

    fun deleteQuestionVulnerabilityTest(id: String) = repository.removeQuestionVulnerabilityTest(id)
    fun modifyQuestionVulnerabilityTest(id: String, question: QuestionVulnerabilityTest) = repository.modifyQuestionVulnerabilityTest(id, question)
    fun addQuestionVulnerabilityTest(question: QuestionVulnerabilityTest) = repository.createQuestionVulnerabilityTest(question)

    val questionsVulnerabilityTestLiveData: LiveData<List<QuestionVulnerabilityTest>> = repository.getQuestionsVulnerabilityTest()

    fun calculateVulnerabilityByType(type: String, questionsChecked: List<QuestionVulnerabilityTest>, questionsNotChecked: List<QuestionVulnerabilityTest>): Double {
        val totalQuestions = (questionsChecked + questionsNotChecked).filter { it.type == type }
        val totalQuestionsPoints = totalQuestions.fold(0.0) { acumulador, actual -> acumulador + actual.points.absoluteValue }
        val negativeQuestionsPoints = questionsNotChecked
            .filter { it.type == type }
            .filter { it.points < 0.0 }
            .fold(0.0) { acumulador, actual ->
                acumulador + actual.points.absoluteValue
            }
        val positiveQuestionsPoints = questionsChecked
            .filter { it.type == type }
            .filter { it.points > 0.0 }
            .fold(0.0) { acumulador, actual ->
                acumulador + actual.points
            }
        return ((positiveQuestionsPoints + negativeQuestionsPoints) / totalQuestionsPoints) * 100.0
    }

}