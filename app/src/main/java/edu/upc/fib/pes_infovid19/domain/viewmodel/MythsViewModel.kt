package edu.upc.fib.pes_infovid19.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import edu.upc.fib.pes_infovid19.data.MythFirebaseRepository
import edu.upc.fib.pes_infovid19.domain.repository.MythsRepository
import edu.upc.fib.pes_infovid19.domain.structures.Myth

private const val MYTHS_NAME = "myths"

class MythsViewModel : ViewModel() {
    private val repository: MythsRepository = MythFirebaseRepository()

    fun deleteMyth(id: String) = repository.removeMyth(id)
    fun modifyMyth(id: String, myth: Myth) = repository.modifyMyth(id, myth)
    fun addMyth(myth: Myth) = repository.createMyth(myth)

    val mythsLiveData: LiveData<List<Myth>> = repository.getMyths()
}



