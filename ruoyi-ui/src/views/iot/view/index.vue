<template>
  <i-frame :src="url" />
</template>
<script>
import iFrame from "@/components/iFrame/index";
export default {
  name: "view",
  components: { iFrame },
  data() {
    return {
      url: "http://view.ashufa.com"
    };
  },
};
</script>
