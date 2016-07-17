/*----------------------------------------------------------------------------------------------*/
/*	GameInfoの説明																				*/
/*    戻り値			メソッド名と説明														*/
/*    int				getDay():日にちを返す													*/
/*    Role      		getRole():自分の役職を返す												*/
/*    Agent     		getAgent():自分の(Agent型)を返す										*/
/*    List<Agent>   	getAgentList():全プレイヤーのリストを返す								*/
/*    Species       	getMediumResult():霊能結果を返す（霊媒師のみ）							*/
/*    Species       	getDivineResult():占い結果を返す（占い師のみ）							*/
/*    Agent         	getExecutedAgent():処刑されたプレイヤーを返す							*/
/*    Agent				getAttackedAgent():襲撃されたプレイヤーを返す							*/
/*    List<Vote>    	getVoteList():処刑の際の投票リストを返す								*/
/*    List<Vote>    	getAttakVoteList:襲撃の際の人狼による投票リストを返す（人狼のみ）		*/
/*    List<Talk>    	getTalkList():会話のログを返す											*/
/*    List<Talk>    	getWhisperList():ささやきのログを返す(人狼のみ)							*/
/*    List<Agent>   	getAliveAgentList():生きているプレイヤーのリストを返す					*/
/*    Map<Agent,Status> getStatusMap():各プレイやーの生死の状態を返す							*/
/*    Map<Agent,Role> 	getRoleMap():各プレイヤーの役職を返す									*/
/* 								 	 村人なら自分のみ、人狼は仲間も								*/
/*								   	 ゲーム終了時は全員の分を返す								*/
/*----------------------------------------------------------------------------------------------*/
package org.aiwolf.GotKAgent;

import org.aiwolf.client.base.player.AbstractPossessed;
import org.aiwolf.common.data.Agent;

/*----------------------------------------------------------------------------------------------*/
/*  狂人クラス																				*/
/*----------------------------------------------------------------------------------------------*/
public class GotKPossessed extends AbstractPossessed {

	@Override
	public void finish() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String talk() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Agent vote() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dayStart() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
