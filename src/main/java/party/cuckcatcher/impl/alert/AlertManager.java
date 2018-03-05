package party.cuckcatcher.impl.alert;

import lombok.Getter;

/**
 * Made by SkidRevenant at 04/03/2018
 */
public class AlertManager {

    private long lastAlert;
    private final int timeToExpire;
    private long[] alerts;
    private int[] alertVls;

    @Getter
    private int currentVl, i = 0;

    public AlertManager(int timeToExpire) {
        this.timeToExpire = timeToExpire * 10000;
        this.lastAlert = System.currentTimeMillis() - 250;
    }

    public boolean alert() {
        this.checkRenew(this.alerts == null);
        this.setAlerts();

        this.i = ++this.i == 512 ? 0 : this.i;

        this.addVl();

        if (this.currentVl < 1) return false;

        if (System.currentTimeMillis() - this.lastAlert < 250) return false;

        this.lastAlert = System.currentTimeMillis();
        return true;
    }

    private void addVl() {
        this.currentVl = 0;

        int vl = this.i;
        int index;
        for (index = 0; index < 512; index++) {
            vl--;

            if (vl < 0) vl = 511;

            if (shouldBreak(vl)) break;

            this.currentVl += this.alertVls[vl];
        }
    }

    private boolean shouldBreak(int index) {
        return this.alertVls[index] < 0 || System.currentTimeMillis() - this.alerts[index] > this.timeToExpire;
    }

    private void setAlerts() {
        this.alerts[i] = System.currentTimeMillis();
        this.alertVls[i] = 1;
    }

    private void checkRenew(boolean shouldRenew) {
        if (shouldRenew) {
            this.alerts = new long[512];
            this.alertVls = new int[512];

            for (int i = 0, len = this.alerts.length; i < len; i++) {
                this.alerts[i] = (long) -1.0;
            }
        }
    }

}
