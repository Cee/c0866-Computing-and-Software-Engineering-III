package excalibur.game.presentation.ui;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.myuicomponent.DialogCreator;

import javax.swing.*;

/**
 * Created by Xc on 2014/6/20.
 */
public class ItemPanelHelper {
    MyItemPanel itemPanel;
    DataUtils.Shipin[] selectedShipins = new DataUtils.Shipin[3];

    DataUtils.Shipin shipinForStrengthen;

    MyItemPanel.MovingLabel inMoving = null;

    int newProp = -1;
    private int goldOnce = 1000;


    ItemPanelHelper(MyItemPanel itemPanel) {
        this.itemPanel = itemPanel;
    }

    void selectStrength(MyItemPanel.MovingLabel label) {
        shipinForStrengthen = label.shipin;

        label.moveTo(itemPanel.shipinPanelX, itemPanel.shipinPanelY);
        System.out.println(String.format("当前效果: %d, 最大效果: %d", label.shipin.current_effect, label.shipin.max_effect));
        if (inMoving != null) {
            inMoving.moveBack();
        }
        inMoving = label;
        itemPanel.propBeforeStrengthenLabel.setText(getDisp(shipinForStrengthen,shipinForStrengthen.current_effect));
        itemPanel.propAfterStrengthenLabel.setText("");

        //qianghua
        if (label.shipin.current_effect >= label.shipin.max_effect) {
            itemPanel.strengthenjb.setEnabled(false);
        } else {
            itemPanel.strengthenjb.setEnabled(true);
        }
        newProp = -1;
        //save
        itemPanel.savejb.setEnabled(false);
    }

    void select(MyItemPanel.MovingLabel label) {
        selectedShipins[label.shipin.type] = label.shipin;
        itemPanel.selectedlbs[label.shipin.type].setIcon(new ImageIcon("img/decorate/" + label.shipin.name + ".png"));
    }

    void unSelectStrength() {
        shipinForStrengthen = null;
        inMoving.moveBack();
        inMoving = null;
        itemPanel.propBeforeStrengthenLabel.setText("");
        itemPanel.propAfterStrengthenLabel.setText("");


        //qianghua
        itemPanel.strengthenjb.setEnabled(false);
        newProp = -1;
        //save
        itemPanel.savejb.setEnabled(false);

    }

    void unselect(int i) {
        if (i >= 0 && i < 3) {
            itemPanel.selectedlbs[i].setIcon(null);
            selectedShipins[i] = null;
        }
    }

    void strength() {
        if (shipinForStrengthen == null) {
            System.out.println("未选择");
        } else {
            if (DataUtils.getInstance().getGold() < goldOnce) {
                DialogCreator.oneButtonDialog("金币不足","金币不足！余额：" + DataUtils.getInstance().getGold());
                return;
            }
            int temp = shipinForStrengthen.current_effect + (int) (Math.random() * 8 - 2);
            if (!DataUtils.getInstance().consumeGold(goldOnce)) {
                DialogCreator.oneButtonDialog("金币不足","金币不足！余额：" + DataUtils.getInstance().getGold());
                return;
            }
            newProp = temp > shipinForStrengthen.init_effect ? temp : shipinForStrengthen.init_effect;
            newProp = newProp <= shipinForStrengthen.max_effect ? newProp : shipinForStrengthen.max_effect;
//            System.out.println(String.format("强化后效果: %d, 最大效果: %d,金币余额：%d", newProp, shipinForStrengthen.max_effect, DataUtils.getInstance().getGold()));


            itemPanel.propAfterStrengthenLabel.setText(getDisp(shipinForStrengthen,newProp));
            itemPanel.goldCountCt.setText("    "+DataUtils.getInstance().getGold());

            if (newProp >= shipinForStrengthen.max_effect) {
                itemPanel.strengthenjb.setEnabled(false);
            }

            itemPanel.savejb.setEnabled(true);
        }
    }

    void save() {
        if (shipinForStrengthen == null) {
            System.out.println("未选择");
        } else {
            if (newProp >= 0) {
                shipinForStrengthen.current_effect = newProp;
//                System.out.println(createDisp(shipinForStrengthen));
                DataUtils.getInstance().updataShipin(shipinForStrengthen);
                System.out.println("保存效果: " + newProp);

                itemPanel.propAfterStrengthenLabel.setText("");
                itemPanel.propBeforeStrengthenLabel.setText(getDisp(shipinForStrengthen, newProp));
            } else {
                System.out.println("未强化");
            }
        }
        itemPanel.savejb.setEnabled(false);
    }

    String createDisp(DataUtils.Shipin s) {
        return String.format("名称:%s\n+效果类型：%d\n效果:%d\n类型", s.name, s.effect_type, s.current_effect, s.type);
    }

    void saveItem() {
        DataUtils.getInstance().saveSelectedShipin(selectedShipins);
    }


    String getDisp(DataUtils.Shipin s,int current_effect){
        String effect = String.format(s.discription[s.effect_type],s.init_effect,s.max_effect, current_effect);
        String type = DataUtils.Shipin.types[s.type];
        String desp = String.format("<html>类型:%s<br>效果：%s</html>",type,effect);
        return desp;

    }
}


