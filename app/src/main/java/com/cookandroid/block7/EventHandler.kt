package com.cookandroid.block7

import android.content.Context
import kotlin.random.Random

// EventHandler : 이벤트 객체 생성, 확률 조정 등
class EventHandler(GameActivity: GameActivity) {
    var GameActivity: GameActivity = GameActivity

    // event_invasion. : 침입 이벤트
    // evnet_plunder : 약탈 이벤트1호관

    var defaultWeight: Int = 5
    var defaultIsAvailable: Boolean = false

    /* 이벤트 객체 생성 */
    // 군대 엔딩 - 접촉
    var event_ending_army_contact = Event(GameActivity, "event_ending_armyContact", defaultWeight, defaultIsAvailable)
    // 군대 엔딩 - 물자 요구
    var event_ending_army_request = Event(GameActivity, "event_ending_army_request", defaultWeight, defaultIsAvailable)
    // 군대 엔딩 - 노크
    var event_ending_army_knock = Event(GameActivity, "event_ending_army_knock", defaultWeight, defaultIsAvailable)
    // 침입 - 강도
    var event_invasion_robbery = Event(GameActivity, "event_invasion_robbery", defaultWeight, defaultIsAvailable)
    // 침입 - 할머니
    var event_invasion_grandma = Event(GameActivity, "event_invasion_grandma", defaultWeight, defaultIsAvailable)
    // 침입 - 소방관
    var event_invasion_firefighter = Event(GameActivity, "event_invasion_firefighter", defaultWeight, defaultIsAvailable)
    // 약탈 - 노인
    var evnet_plunder_oldMan = Event(GameActivity, "evnet_plunder_oldMan", defaultWeight, defaultIsAvailable)
    // 약탈 - 1호관 동아리방
    var evnet_plunder_b1cr = Event(GameActivity, "evnet_plunder_b1cr", defaultWeight, defaultIsAvailable)
    // 약탈 - 요양원
    var evnet_plunder_nursingHome = Event(GameActivity, "evnet_plunder_nursingHome", defaultWeight, defaultIsAvailable)
    // 약탈 - 연구실
    var evnet_plunder_Lab = Event(GameActivity, "evnet_plunder_oldMan", defaultWeight, defaultIsAvailable)

    // 모든 이벤트 리스트 - 이벤트 객체 생성 시 추가됨 (Event.init에서 추가됨)
    var events = mutableListOf<Event>()
    // 탐험 시 발생할 수 있는 이벤트
    var events_exploring = mutableListOf<Event>()

    /* 가중치를 기반으로 랜덤으로 이벤트 선택 및 실행 */
    fun executeRandomEvent() {
        // do-while 루프를 통해 선택된 이벤트 객체가 실행 불가능한 경우 다시 뽑도록 함
        // isAvailable가 true인 이벤트 객체가 뽑힐 때까지 실행
        var selectedEvent: Event

        do {
            selectedEvent = selectRandomEvent()
        } while (!selectedEvent.isAvailable)

        selectedEvent.executeEventEffect()
    }

    // 가중치를 기반으로 랜덤으로 이벤트 선택
    private fun selectRandomEvent(): Event {
        val totalWeight = events.sumBy { it.weight }

        var randomValue = Random.nextInt(totalWeight)
        var selectedEvent: Event = events.first()

        for (event in events) {
            if (randomValue < event.weight) {
                selectedEvent = event
                break
            }
            randomValue -= event.weight
        }

        return selectedEvent
    }

    /* 이벤트 스크립트 설정 */
    fun setEventScript() {
        event_ending_army_contact.setPreScript(getScript(""))
        event_ending_army_contact.setPostScript(getScript(""))
        event_ending_army_request.setPreScript(getScript(""))
        event_ending_army_request.setPostScript(getScript(""))
        event_ending_army_knock.setPreScript(getScript(""))
        event_ending_army_knock.setPostScript(getScript(""))
        event_invasion_robbery.setPreScript(getScript("event_invasion_robbery_pre"))
        event_invasion_robbery.setPostScript(getScript("event_invasion_robbery_post"))
        event_invasion_grandma.setPreScript(getScript("event_invasion_grandma_pre"))
        event_invasion_grandma.setPostScript(getScript("event_invasion_grandma_post"))
        event_invasion_firefighter.setPreScript(getScript(""))
        event_invasion_firefighter.setPostScript(getScript(""))
        evnet_plunder_oldMan.setPreScript(getScript(""))
        evnet_plunder_oldMan.setPostScript(getScript(""))
        evnet_plunder_b1cr.setPreScript(getScript(""))
        evnet_plunder_b1cr.setPostScript(getScript(""))
        evnet_plunder_nursingHome.setPreScript(getScript(""))
        evnet_plunder_nursingHome.setPostScript(getScript(""))
        evnet_plunder_Lab.setPreScript(getScript(""))
        evnet_plunder_Lab.setPostScript(getScript(""))
        // ... (다른 이벤트에 대한 설정)
    }

    /* events_scripts.txt에서 읽어오는 코드 */
    private val eventScripts: Map<String, String> = readEventScriptsFromResources(R.raw.event_scripts)

    private fun readEventScriptsFromResources(resourceId: Int): Map<String, String> {
        return try {
            val scriptText = GameActivity.resources.openRawResource(resourceId).bufferedReader().use { it.readText() }
            parseEventScripts(scriptText)
        } catch (e: Exception) {
            mapOf()
        }
    }

    private fun parseEventScripts(scriptText: String): Map<String, String> {
        val scripts = mutableMapOf<String, String>()
        val lines = scriptText.split("\n")

        var currentEventKey: String? = null
        var currentEventScript: StringBuilder? = null

        for (line in lines) {
            if (line.startsWith("event_")) {
                currentEventKey?.let { key ->
                    currentEventScript?.let { script ->
                        scripts[key] = script.toString()
                    }
                }
                currentEventKey = line.trim()
                currentEventScript = StringBuilder()
            } else {
                currentEventScript?.append(line)?.append("\n")
            }
        }

        currentEventKey?.let { key ->
            currentEventScript?.let { script ->
                scripts[key] = script.toString()
            }
        }

        return scripts
    }

    fun getScript(eventKey: String): String {
        return eventScripts[eventKey] ?: ""
    }
}
