<?php
/**
 * Allow switching title from env
 */
class EnvTitle {
        private static function strip($color) {
                $ret = preg_replace("/[^a-zA-Z0-9#]/", "", $color);
                return $ret;
        }

        function navigation($missing) {
                $color = self::strip(getenv("TITLE_COLOR") ?: "black");
                $background = self::strip(getenv("TITLE_BACKGROUND") ?: "black");
                $title = self::strip(getenv("TITLE_TEXT") ?: gethostname());
                echo "<h1 style=\"color:$color;background-color:$background;\">$title</h1>";
        }

}

return new EnvTitle();
