var org = {jboss: {schlawiner: {}}};

org.jboss.schlawiner.Countdown = function (e, w, h, r) {
    this.paper = new Raphael(e, w, h);
    this.width = w;
    this.height = h;
    this.rad = r;
    this.value = 0;
    this.threshold = 0;
    this.total = 0;
    this.arcElement = null;
    this.numberElement = null;
    this.color = "rgba(153, 153, 153, .25)";
    this.warning = "rgba(251, 102, 102, .25)";

    var self = this;
    this.paper.customAttributes.arc = function (value, total) {
        var cx = self.width / 2,
            cy = self.height / 2,
            alpha = 360 / total * value,
            a = (90 - alpha) * Math.PI / 180,
            x = cx + self.rad * Math.cos(a),
            y = cy - self.rad * Math.sin(a),
            path;
        if (value === total) {
            path = [
                ["M", cx, cy - self.rad],
                ["A", self.rad, self.rad, 0, 1, 1, cx - 0.01, cy - self.rad]
            ];
        } else {
            path = [
                ["M", cx, cy - self.rad],
                ["A", self.rad, self.rad, 0, (alpha > 180) ? 1 : 0, 1, x, y]
            ];
        }
        return {path: path};
    };
};

org.jboss.schlawiner.Countdown.prototype.reset = function (total) {
    if (this.arcElement != null) {
        this.arcElement.remove();
    }
    this.value = 0;
    this.total = total;
    this.threshold = total * (80 / 100);
    this.arcElement = this.paper.path().attr({"arc": [0, this.total], "stroke": this.color, "stroke-width": 15});
};

org.jboss.schlawiner.Countdown.prototype.tick = function () {
    if (this.arcElement != null) {
        this.value++;
        var c = (this.value < this.threshold ? this.color : this.warning);
        this.arcElement.attr({stroke: c});
        this.arcElement.animate({arc: [this.value, this.total]}, 750, "bounce");
        return this.value < this.total;
    }
    return false;
};

org.jboss.schlawiner.Countdown.prototype.number = function (number) {
    if (this.numberElement == null) {
        this.numberElement = this.paper.text(this.width / 2, this.height / 2, " ").attr({
            "font-family": "Architects Daughter",
            "font-size": "48px",
            fill: "rgb(236, 236, 236)"
        });
    }
    if (number > 0) {
        this.numberElement.attr({text: number});
    }
};
