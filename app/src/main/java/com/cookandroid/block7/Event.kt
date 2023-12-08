package com.cookandroid.block7

// Event: MainGame, 이벤트 이름, 호출 가능 여부, 가중치
class Event(GameActivity: GameActivity, eventName: String,  weight: Int, isAvailable: Boolean) {
    // 게임 액티비티
    var GameActivity: GameActivity = GameActivity
    // 이벤트 이름
    var eventName: String = eventName
    // 가중치
    var weight: Int = weight
    // 호출 가능 여부
    var isAvailable: Boolean = isAvailable

    // 스크립트
    private var preScript: String = ""
    private var postScript: String = ""

    /* 이벤트의 isAvailable 설정 메소드 */
    fun setIsAailable() {
        when (eventName) {
            "event_invasion_robbery" -> setIsAailable_event_invasion_robbery()
            "event_invasion_grandma" -> setIsAailable_event_invasion_grandma()
            "event_invasion_firefighter" -> setIsAailable_event_invasion_firefighter()
            "evnet_plunder_oldMan" -> setIsAailable_evnet_plunder_oldMan()
            "evnet_plunder_b1cr" -> setIsAailable_evnet_plunder_b1cr()
            "evnet_plunder_nursingHome" -> setIsAailable_evnet_plunder_nursingHome()
            "evnet_plunder_Lab" -> setIsAailable_evnet_plunder_Lab()
            // 추가적인 이벤트에 대한 처리
            else -> {
                // 정의되지 않은 이벤트에 대한 기본 처리
            }
        }
    }

    // 각 이벤트의 isAvailable 및 weight 수정
    private fun setIsAailable_event_ending_army_contact() {

    } private fun setIsAailable_event_ending_army_request() {

    } private fun setIsAailable_event_ending_army_knock() {

    } private fun setIsAailable_event_invasion_robbery() {

    } private fun setIsAailable_event_invasion_grandma() {

    } private fun setIsAailable_event_invasion_firefighter() {

    } private fun setIsAailable_evnet_plunder_oldMan() {

    } private fun setIsAailable_evnet_plunder_b1cr() {

    } private fun setIsAailable_evnet_plunder_nursingHome() {

    } private fun setIsAailable_evnet_plunder_Lab() {

    }

    /* 이벤트 실행 메소드 */
    fun executeEventEffect() {
        // 아이템
        val itemListTmp = GameActivity.itemList
        val itemListOwnedTmp = GameActivity.itemListOwned
        val itemListBrokenTmp = GameActivity.itemListBroken
        // 식량
        val kimbabTmp = GameActivity.food_kimbap
        val waterTmp = GameActivity.food_water
        // 멤버
        val memberListTmp = GameActivity.memberList

        /* 이벤트 실행 */
        var tmp = 1
        when (eventName) {
            "event_invasion_robbery" -> event_invasion_robbery_effect(tmp)
            "event_invasion_grandma" -> event_invasion_grandma_effect(tmp)
            "event_invasion_firefighter" -> event_invasion_firefighter_effect(tmp)
            "evnet_plunder_oldMan" -> evnet_plunder_oldMan_effect(tmp)
            "evnet_plunder_b1cr" -> evnet_plunder_b1cr_effect(tmp)
            "evnet_plunder_nursingHome" -> evnet_plunder_nursingHome_effect(tmp)
            "evnet_plunder_Lab" -> evnet_plunder_Lab_effect(tmp)
            // 추가적인 이벤트에 대한 처리
            else -> {
                // 정의되지 않은 이벤트에 대한 기본 처리
            }
        }

        /* postScript 수정 */
        // 아이템
        for(item in itemListTmp) {
            // 가지고 있었고 부서진 경우
            // 가지고 있었고 잃은 경우
            // 가지고 있지 않았고 얻은 경우
            if(item in itemListOwnedTmp) { // 원래 가지고 있던 경우
                if(item in GameActivity.itemListBroken) { postScript += item.nameKorItem + "이(가) 고장났다.\n" }
                if(item !in GameActivity.itemListOwned) { postScript += item.nameKorItem + "을(를) 잃었다.\n" }
            } else { // 원래 가지고 있지 않았던 경우
                if(item in GameActivity.itemListOwned) { postScript += item.nameKorItem + "을(를) 얻었다.\n" }
            }
        }
        // 식량
        val numKimbab = GameActivity.food_kimbap.count - kimbabTmp.count
        val numWater = GameActivity.food_water.count - waterTmp.count
        if(numKimbab > 0) { postScript += "김밥을 " + numKimbab +" 개 얻었다.\n" }
        else if(numKimbab < 0) { postScript += "김밥을 " + Math.abs(numKimbab) +" 개 잃었다.\n" }
        if(numWater > 0) { postScript += "물을 " + numWater +" 개 얻었다.\n" }
        else if(numWater < 0) { postScript += "물을 " + Math.abs(numWater) +" 개 잃었다.\n" }
        // 멤버
        for(memberTmp in memberListTmp) {
            val member = GameActivity.memberList.find { it.name == memberTmp.name }
            if(memberTmp.getStateIsCrazy() != member?.getStateIsCrazy()) {
                // isVeryCarzy -> isCrazy로 바뀐 경우, 여기에 걸림. "멤버이(가) 미쳤습니다" 표기
                if(member?.getStateIsCrazy() == true) postScript += member?.nameKor + "이(가) 미쳤습니다.\n"
                if(member?.getStateIsCrazy() == false && member?.getStateIsVeryCrazy() == false) postScript += member?.nameKor + "이(가) 정상으로 돌아왔습니다."
            }
            if(memberTmp.getStateIsVeryCrazy() != member?.getStateIsVeryCrazy()) {
                if(member?.getStateIsVeryCrazy() == true) postScript += member?.nameKor + "이(가) 착란 상태 입니다.\n"
                if(member?.getStateIsVeryCrazy() == false && member?.getStateIsCrazy() == false) postScript += member?.nameKor + "이(가) 정상으로 돌아왔습니다."
            }
            if(memberTmp.getStateIsHurt() != member?.getStateIsHurt()) {
                if(member?.getStateIsHurt() == true) postScript += member?.nameKor + "이(가) 다쳤습니다.\n"
                if(member?.getStateIsHurt() == false && member?.getStateIsVeryHurt() == false) postScript += member?.nameKor + "이(가) 정상으로 돌아왔습니다."
            }
            if(memberTmp.getStateIsVeryHurt() != member?.getStateIsVeryHurt()) {
                if(member?.getStateIsVeryHurt() == true) postScript += member?.nameKor + "이(가) 고통받습니다.\n"
                if(member?.getStateIsVeryHurt() == false && member?.getStateIsHurt() == false) postScript += member?.nameKor + "이(가) 정상으로 돌아왔습니다."
            }
            if(memberTmp.getStateIsSick() != member?.getStateIsSick()) {
                if(member?.getStateIsCrazy() == true) postScript += member?.nameKor + "이(가) 질병에 걸렸습니다.\n"
                if(member?.getStateIsCrazy() == false && member?.getStateIsVeryCrazy() == false) postScript += member?.nameKor + "이(가) 정상으로 돌아왔습니다."
            }
            if(memberTmp.getStateIsVerySick() != member?.getStateIsVerySick()) {
                if(member?.getStateIsCrazy() == true) postScript += member?.nameKor + "이(가) 병에 걸렸습니다.\n"
                if(member?.getStateIsCrazy() == false && member?.getStateIsVeryCrazy() == false) postScript += member?.nameKor + "이(가) 정상으로 돌아왔습니다."
            }
            if(memberTmp.getStateIsTired() != member?.getStateIsTired()) {
                if(member?.getStateIsCrazy() == true) postScript += member?.nameKor + "은(는) 피곤합니다.\n"
                if(member?.getStateIsCrazy() == false && member?.getStateIsVeryCrazy() == false) postScript += member?.nameKor + "이(가) 정상으로 돌아왔습니다."
            }
            if(memberTmp.getStateIsFatigued() != member?.getStateIsFatigued()) {
                if(member?.getStateIsCrazy() == true) postScript += member?.nameKor + "이(가) 지쳤습니다.\n"
                if(member?.getStateIsCrazy() == false && member?.getStateIsVeryCrazy() == false) postScript += member?.nameKor + "이(가) 정상으로 돌아왔습니다."
            }
        }
    }


    /* 랜덤 이벤트 */
    // 랜덤으로 가지고 있는 아이템 중 하나를 잃
    fun loseRandomItem() {
        var selectedItem: Item? = GameActivity.getRandomItemitemListOwned()
        if(selectedItem != null) selectedItem.loseItem()
    }
    // 랜덤으로 식량을 잃고, 식량 이름과 잃은 개수를 반환함
    fun loseRandomFood(start: Int, end: Int){ // < 식량 객체, 잃은 개수 > 반환
        val selectedFood: Food = GameActivity.getRandomFood() // 랜덤 식량
        selectedFood.loseFoodRandom(start, end) // 해당 식량이 0개인 경우 loseFoodRandom 내부에서 0개로 조정됨. 변경사항 없음.
    }
    // 랜덤 멤버 상태 변화
    fun crazyRandomMember() {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeCrazy()
    }
    fun veryCrazyRandomMember() {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeVeryCrazy()
    }
    fun hurtRandomMember() {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeHurt()
    }
    fun veryHurtRandomMember() {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeVeryHurt()
    }
    fun sickRandomMember() {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeSick()
    }
    fun verySickRandomMember() {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeVerySick()
    }
    fun tiredRandomMember() {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeTired()
    }
    fun fatiguedRandomMember() {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeFatigued()
    }


    /* 각 이벤트에 대한 호출 동작 정의 */
    private fun event_invasion_robbery_effect(tmp: Int) { // 침입 - 강도
        if(tmp == 1) { // 문을 열어준 경우
            loseRandomItem() // 가지고 있는 아이템 중, 랜덤으로 하나를 잃음
            loseRandomFood(1, 3) // 김밥, 물 중 하나를 골라 랜덤으로 1~3개를 잃음
        } else { /* - 문을 열어주지 않은 경우 - */
            setPreScript(GameActivity.eventHandler.getScript("event_invasion_robbery_post_tmp"))
        }
    } private fun event_invasion_grandma_effect(tmp: Int) { // 침입 - 할머니
        if (tmp == 1) { // 문을 열어준 경우
            hurtRandomMember() // 안에 있는 멤버중 랜덤으로 1명이 다침
            loseRandomItem() // 가지고 있는 아이템 중, 랜덤으로 하나를 잃음
        } else { // 문을 열어주지 않은 경우
            setPreScript(GameActivity.eventHandler.getScript("event_invasion_grandma_effect_post_tmp"))
        }
    } private fun event_invasion_firefighter_effect(tmp: Int) { // 침입 - 소방관
        if (tmp == 1) { // 문을 열어준 경우
            loseRandomFood(1, 3) // 김밥, 물 중 하나를 골라 랜덤으로 1~3개를 잃음
        } else { // 문을 열어주지 않은 경우
            
        }
    } private fun evnet_plunder_oldMan_effect(tmp: Int) { // 약탈 - 노인
        // event3에 대한 동작 정의
        // 예: 특별한 이펙트 발동, 게임 진행 상태 변경 등
    } private fun evnet_plunder_b1cr_effect(tmp: Int) { // 약탈 - 1호관 동아리방
        // event3에 대한 동작 정의
        // 예: 특별한 이펙트 발동, 게임 진행 상태 변경 등
    } private fun evnet_plunder_nursingHome_effect(tmp: Int) { // 약탈 - 요양원
        // event3에 대한 동작 정의
        // 예: 특별한 이펙트 발동, 게임 진행 상태 변경 등
    } private fun evnet_plunder_Lab_effect(tmp: Int) { // 약탈 - 연구실
        // event3에 대한 동작 정의
        // 예: 특별한 이펙트 발동, 게임 진행 상태 변경 등
    }

    // 스크립트 설정 메소드
    fun setPreScript(str: String) { preScript = str }
    fun setPostScript(str: String) { postScript = str }
    fun setPostScriptNull() { postScript = "" } // 아무일도 일어나지 않을 경우 호출
    fun addPostScriptLoseNothing(str: String) { postScript += str }
    fun addPostScriptLoseItem(itemName: String) { postScript += itemName + "을(를) 잃었습니다.\n" }
    fun addPostScriptLoseFood(foodName: String, cnt: Int) { postScript += foodName + "을(를) " + cnt.toString() +"개 잃었습니다.\n" }
    fun addPostScriptHurtMamber(memberName: String) { postScript += memberName + "이(가) 다쳤습니다.\n" }
}