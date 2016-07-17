package org.aiwolf.GotKAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.aiwolf.client.base.player.AbstractVillager;
import org.aiwolf.common.data.Agent;

public class GotKVillager extends AbstractVillager {

	@Override
	public void dayStart() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void finish() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String talk()
	{
		return null;
	}

	@Override
	public Agent vote()
	{
		List<Agent> voteCandidates = new ArrayList<Agent>();
		voteCandidates.addAll(getLatestDayGameInfo().getAliveAgentList());
		voteCandidates.remove(getMe());

		return randomSelect(voteCandidates);
	}

	private Agent randomSelect(List<Agent> agentList)
	{
		int num=new Random().nextInt(agentList.size());
		return agentList.get(num);
	}

}
