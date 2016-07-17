package org.aiwolf.GotKAgent;

import org.aiwolf.client.base.player.AbstractRoleAssignPlayer;

public class GotKRoleAssignPlayer extends AbstractRoleAssignPlayer
{
	/*-----------------------------------------------------------------*/
	/*	振り分けられた役職の呼出クラス								   */
	/*-----------------------------------------------------------------*/

	public GotKRoleAssignPlayer()
	{
		setSeerPlayer(new GotKSeer());//占い師
		setBodyguardPlayer(new GotKBodyguard());//狩人
		setMediumPlayer(new GotKMedium());//霊媒師
		setPossessedPlayer(new GotKPossessed());//狂人
		setWerewolfPlayer(new GotKWerewolf());//人狼
		setVillagerPlayer(new GotKVillager());//村人
	}

	@Override
	public String getName()
	{
		// TODO 自動生成されたメソッド・スタブ

		return "GotK";
	}

}
