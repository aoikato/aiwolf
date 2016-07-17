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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.aiwolf.client.base.player.AbstractSeer;
import org.aiwolf.client.lib.TemplateTalkFactory;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Species;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.net.GameInfo;

/*----------------------------------------------------------------------------------------------*/
/*  占い師クラス																				*/
/*----------------------------------------------------------------------------------------------*/
public class GotKSeer extends AbstractSeer
{
	/*-------------------------------------------------------------------------------------------*/
	/*  メソッド名：divine													 				     */
	/*	占いする相手を選択するメソッド 										 				     	 */
	/*  占い師のみ呼ばれる																		 */
	/*-------------------------------------------------------------------------------------------*/
	@Override
	public Agent divine()
	{
		// 占い対象の候補者リスト
		List<Agent> divineCandidates = new ArrayList<Agent>();

		// 生存プレイヤーを候補者リストに加える
		divineCandidates.addAll(getLatestDayGameInfo().getAliveAgentList());

		// 自分自身は候補者リストから除外
		divineCandidates.remove(getMe());
		// 占い済みのプレイヤーは候補から除外
		for(Judge judge: getMyJudgeList())
		{
			if(divineCandidates.contains(judge.getTarget()))
			{
				divineCandidates.remove(judge.getTarget());
			}
		}
		if(divineCandidates.size() > 0)
		{
			//候補者リストからランダムに選択
			return randomSelect(divineCandidates);
		}
		else
		{
			//候補者がいない場合は自分を占う
			return getMe();
		}
	}

	/*-------------------------------------------------------------------------------------------*/
	/*  メソッド名：finish													 				     */
	/*	ゲーム終了時に呼ばれるメソッド 										 				     */
	/*-------------------------------------------------------------------------------------------*/
	@Override
	public void finish() {
		// TODO 自動生成されたメソッド・スタブ

	}

	/*-------------------------------------------------------------------------------------------*/
	/*  メソッド名：talk													 				     */
	/*	発話するときに呼ばれるメソッド 										 				     */
	/*-------------------------------------------------------------------------------------------*/
	boolean isComingOut = false; //
	List<Judge> myToldJudgeList = new ArrayList<Judge>();

	@Override
	public String talk()
	{
		//COしていない場合
		if(!isComingOut)
		{
			for(Judge judge: getMyJudgeList())
			{
				//占い結果が人狼の場合
				if(judge.getResult() == Species.WEREWOLF)
				{
					//自分の役職をCO
					String comingoutTalk = TemplateTalkFactory.comingout(getMe(), getMyRole());
					isComingOut = true;
					return comingoutTalk;
				}
			}
		}
		else
		{
			// 占い結果を報告
			for(Judge judge:getMyJudgeList())
			{
				//まだ報告していない占い結果を報告
				if(!myToldJudgeList.contains(judge))
				{
					String resultTalk = TemplateTalkFactory.divined(judge.getTarget(), judge.getResult());
					myToldJudgeList.add(judge);
					return resultTalk;
				}
			}
		}

		// 話すことがなければ会話終了
		return Talk.OVER;
	}

	/*-------------------------------------------------------------------------------------------*/
	/*  メソッド名：vote													 				     */
	/*	投票する相手を選択するときに呼ばれるメソッド 										 	 */
	/*-------------------------------------------------------------------------------------------*/
	@Override
	public Agent vote()
	{
		List<Agent> whiteAgent = new ArrayList<Agent>(), // 白判定プレイヤーリスト作成
				    blackAgent = new ArrayList<Agent>(); // 黒判定プレイヤーリスト作成

		//今までに占ったプレイヤーをwhiteかblackに分ける
		for(Judge judge: getMyJudgeList())
		{
			if(getLatestDayGameInfo().getAliveAgentList().contains(judge.getTarget()))
			{
				switch (judge.getResult())
				{
					case HUMAN:
						whiteAgent.add(judge.getTarget());
						break;

					case WEREWOLF:
						blackAgent.add(judge.getTarget());
						break;
				}
			}
		}

		if(blackAgent.size() > 0)
		{
			// blackagentがいればその中からランダムに選択
			return randomSelect(blackAgent);
		}
		else
		{
			// 投票対象の候補者リスト作成
			List<Agent> voteCandidates = new ArrayList<Agent>();

			voteCandidates.addAll(getLatestDayGameInfo().getAliveAgentList());

			// 自分自身を候補者リストから除外
			voteCandidates.remove(getMe());
			// 白判定のプレイヤーを除外
			voteCandidates.removeAll(whiteAgent);

			// 残った候補者リストの中からランダムに選択
			return randomSelect(voteCandidates);
		}

	}

	/*-------------------------------------------------------------------------------------------*/
	/*  メソッド名：dayStart													 				 */
	/*	日の初めに呼ばれるメソッド 										 				         */
	/*-------------------------------------------------------------------------------------------*/
	int readTalkNum = 0; //ログナンバー
	@Override
	public void dayStart()
	{
		super.dayStart();
		readTalkNum = 0;
	}

	/*-------------------------------------------------------------------------------------------*/
	/*  メソッド名：update													 					 */
	/*	各行動の前に呼ばれるメソッド 										 				     */
	/*	サーバから送られてくるGameInfoを取得  								 					 */
	/*-------------------------------------------------------------------------------------------*/
	List<Agent> fakeSeerCOAgent = new ArrayList<Agent>();// 偽占い師リスト
	@Override
	public void update(GameInfo gameInfo)
	{
		super.update(gameInfo);

		//今日の会話ログを取得
		List<Talk> talkList = gameInfo.getTalkList();

		for(int i=readTalkNum;i<talkList.size(); i++)
		{
			Talk talk = talkList.get(i);

			//コンストラクタで会話内容を自動的にパース
			//talk.getContent()="DIVINED Agent[04] HUMAN"(ex)
			Utterance utterance = new Utterance(talk.getContent());

			// 発話の内容ごとに処理
			switch(utterance.getTopic())
			{
				// 発話内容：CO
				case COMINGOUT:
					//自分が占い師 && 占い師COしているプレイヤーが他にいる場合
					if(utterance.getRole() == Role.SEER && !talk.getAgent().equals(getMe()))
					{
						fakeSeerCOAgent.add(utterance.getTarget());
					}
					break;
				// 発話内容：占い
				case DIVINED:
					break;
				//
				case AGREE:
					break;
				//
				case ATTACK:
					break;
				//
				case DISAGREE:
					break;
				//
				case ESTIMATE:
					break;
				//
				case GUARDED:
					break;
				//
				case INQUESTED:
					break;
				//
				case OVER:
					break;
				//
				case SKIP:
					break;
				//
				case VOTE:
					break;

				default:
					break;
			}
			readTalkNum++;
		}
	}

	/*-------------------------------------------------------------------------------------------*/
	/*  メソッド名：randomSelect													 			 */
	/*	ランダムにプレイヤーを選択するときに呼ばれるメソッド 									 */
	/*-------------------------------------------------------------------------------------------*/
	private Agent randomSelect(List<Agent> agentList)
	{
		int num = new Random().nextInt(agentList.size());
		return agentList.get(num);
	}

}
