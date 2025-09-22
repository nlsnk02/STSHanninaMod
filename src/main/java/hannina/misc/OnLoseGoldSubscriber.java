package hannina.misc;

public interface OnLoseGoldSubscriber {
    //暂时只能用于牌,能力,遗物，药水
    void onLoseGold(int gold);
}
