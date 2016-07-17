package org.aiwolf.GotKAgent;

import org.aiwolf.client.base.player.AbstractRoleAssignPlayer;

public class GotKRoleAssignPlayer extends AbstractRoleAssignPlayer
{
	/*-----------------------------------------------------------------*/
	/*	振り分けられた役職の呼出クラス								   */
	/*-----------------------------------------------------------------*/

	// 占い師の呼出
	public GotKRoleAssignPlayer()
	{
		setSeerPlayer(new GotKSeer());
	}
	@Override
	public String getName()
	{
		// TODO 自動生成されたメソッド・スタブ

		return null;
	}

}
