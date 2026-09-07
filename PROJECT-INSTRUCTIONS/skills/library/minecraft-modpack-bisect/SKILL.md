---
name: minecraft-modpack-bisect
source: user-supplied minecraft-modpack-bisect.zip
project_status: preferred
---
# Minecraft Modpack Bisect

Use quando a falha é reproduzível, mas logs e pesquisa não isolam o mod/interação exatos.

Preserve um baseline reproduzível, divida o conjunto de suspeitos, altere somente uma variável experimental por rodada e mantenha ledger de cada execução. Não bisecte dependências obrigatórias de forma que invalide o teste. O resultado deve ser a menor combinação causal conhecida, não apenas “um mod que sumiu quando removido”.
