package edu.upc.fib.pes_infovid19.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.upc.fib.pes_infovid19.domain.repository.RiskPreventionRepository
import edu.upc.fib.pes_infovid19.domain.structures.Prevention
import edu.upc.fib.pes_infovid19.domain.structures.RiskPrevention
import java.util.*

private const val PREVENTION_NAME = "prevencio"

class RiskPreventionFirebaseRepository : RiskPreventionRepository {
    private val database = Firebase.database.reference.child(PREVENTION_NAME)
    private val _preventionLiveData = MutableLiveData<List<RiskPrevention>>().also { data ->
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items = if (snapshot.exists()) snapshot.children.mapNotNull { it.getValue(RiskPrevention::class.java) }.toList()
                else emptyList()
                val ids: MutableList<String> = ArrayList()
                for (snap in snapshot.children) {
                    ids.add(snap.key.toString())
                }
                items = setRiskPreventionId(items, ids)
                data.postValue(items)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun getRiskPrevention(): LiveData<List<RiskPrevention>> = _preventionLiveData

    override fun removeRiskPrevention(id: String) {
        database.child(id).removeValue()
    }

    override fun removePrevention(idRiskPrevention: String, id: String) {
        database.child(idRiskPrevention).child(id)
    }

    override fun modifyRiskPrevention(id: String, riskPrevention: RiskPrevention, listCreatedPrevention: List<Prevention>) {
        database.child(id).setValue(riskPrevention)
        listCreatedPrevention.forEach {
            createPrevention(id, it)
        }

    }

    override fun modifyPrevention(idRiskPrevention: String, id: String, prevention: Prevention) {
        database.child(idRiskPrevention).child(id).setValue(prevention)
    }

    override fun createRiskPrevention(riskPrevention: RiskPrevention) {
        database.push().setValue(riskPrevention)
    }

    override fun createPrevention(idRiskPrevention: String, prevention: Prevention) {
        database.child(idRiskPrevention).child("recomanacions").push().setValue(prevention)
    }

    private fun setRiskPreventionId(items: List<RiskPrevention>, ids: List<String>): List<RiskPrevention> {
        var i = 0
        for (item in items) {
            item.id = ids.get(i)
            i += 1
        }
        return items
    }

    private fun setPreventionId(items: List<Prevention>, ids: List<String>): List<Prevention> {
        var i = 0
        for (item in items) {
            item.id = ids.get(i)
            i += 1
        }
        return items
    }

}