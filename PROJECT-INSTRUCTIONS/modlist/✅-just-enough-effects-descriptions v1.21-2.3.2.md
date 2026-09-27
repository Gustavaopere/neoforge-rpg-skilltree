# Just Enough Effects Descriptions

## Propriedades do registro

- **Mod:** Just Enough Effects Descriptions
- **Arquivo JAR:** jeed-1.21-2.3.2.jar
- **Versão 1.21.1:** 1.21-2.3.2
- **Categoria:** QoL
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/just-enough-effect-descriptions-jeed/files/7346553
- **Função:** Plugin de interface que indexa e exibe descrições/informações de efeitos de status e poções registrados no jogo dentro de viewers compatíveis como JEI, REI ou EMI; no pack atual a integração concreta é JEI.
- **Dependências:** Viewer compatível. Pack físico usa JEI 19.56.0.440; não foram identificados EMI/REI como top-level nesta matriz. Release 2.3.2 é NeoForge 1.21.1/1.21 e funciona como plugin de visualização.
- **Compatibilidade/Riscos:** Display-only plugin. Riscos: descrição ausente/incorreta para efeito modded, localization/data drift, recipe/viewer API drift e UI ilegível. 2.3.2 corrige especificamente legibilidade da recipe no EMI; isso não implica que EMI esteja instalado no pack.
- **Sobreposição:** Não cria/modifica efeitos e não substitui JEI. Complementa o viewer com documentação de status effects; qualquer valor real de duração/amplifier/mecânica continua pertencendo ao mod que registra o efeito.
- **Observações:** 2.3.2 continua a build NeoForge correta para 1.21.1. O projeto possui linhas 2.4/2.5 mais novas em Minecraft 1.21.11/26.x, mas elas não constituem atualização aplicável ao pack atual.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge oficial JEED file 7346553 `jeed-1.21-2.3.2.jar`, Release NeoForge para 1.21/1.21.1 + listagem atual de arquivos. Integração física reconciliada com JEI 19.56.0.440; versões 2.4.x/2.5.x pertencem a outras linhas de Minecraft.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — JEED 1.21-2.3.2/JAR físico reconfirmado; viewer/display-only authority, integração concreta com JEI 19.56.0.440, fix de legibilidade EMI 2.3.2, localization/data risks e testes preservados.

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #348: JAR `jeed-1.21-2.3.2.jar`, mod id `jeed`, runtime `1.21-2.3.2`, SHA-1 `144ffaf459b58db14614a7e1ef3425c2594e30b6`.

<callout icon="🧪" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `jeed-1.21-2.3.2.jar`, mod id `jeed`, versão `1.21-2.3.2`. CurseForge file 7346553 confirma Release NeoForge para Minecraft 1.21.1/1.21 em 17/12/2025. JEED é plugin informativo; não altera os efeitos que descreve.
</callout>
## 1. Papel e authority
Just Enough Effects Descriptions adiciona informação sobre potion/status effects a recipe/item viewers compatíveis. O mod que registra cada efeito permanece authority de duração, amplifier, damage, heal, attributes e lógica de aplicação; JEED apenas apresenta documentação/interface.
## 2. Viewer atual no pack
O projeto suporta **JEI, REI ou EMI**. Na modlist física atual, o viewer concreto é **JEI 19.56.0.440**. Ausência de REI/EMI top-level significa que suporte a esses viewers não deve ser tratado como integração ativa deste pack.
## 3. Efeitos vanilla e modded
JEED procura apresentar informação para efeitos registrados no jogo, incluindo efeitos de mods quando há dados/descrição disponível. Cobertura visual não garante que todo efeito modded tenha texto perfeito; missing description é problema documental/UI, não ausência do efeito no registry.
## 4. Display-only boundary
A tela do viewer não é gameplay authority. Abrir uma descrição, pesquisar um effect ou visualizar uma recipe não concede potion/effect, não dispara advancement e não deve ser usado por quests como evidência de aplicação real.
## 5. Localização
Descrições dependem de localization/data. Resource packs ou traduções podem alterar texto sem alterar comportamento do efeito. Uma descrição divergente do runtime deve ser tratada como documentação stale até conferir o provider, não como regra do servidor.
## 6. Release 2.3.2
O changelog exato da build instalada contém um único fix publicado: **correção da legibilidade da recipe no EMI**. Como EMI não está top-level no pack, esse fix permanece provenance da release, mas não é uma integração corrente que precise ser presumida.
## 7. Relação com JEI
JEI fornece o framework de categories/ingredients/recipes/UI; JEED registra sua apresentação de effect descriptions nesse ecossistema. Update de JEI pode quebrar plugin API/rendering mesmo que os status effects do jogo continuem funcionais.
## 8. Client / server
O valor funcional de JEED é predominantemente client-side/UI. O servidor continua owner dos efeitos e não deve depender de JEED para aplicar rules. Se uma distribuição exigir presença bilateral por metadata, isso não muda a authority: a descrição continua não authoritative.
## 9. Lifecycle
Validar client boot, viewer/plugin registration, resource reload, language change, JEI reload e entrada/saída de mundo. Cache de descrição não deve manter texto de um registry/resource state antigo após reload.
## 10. Riscos técnicos
- JEI/API drift quebrar plugin registration;
- efeito modded sem descrição adequada;
- localization stale ou misleading;
- recipe/display ilegível em viewer alternativo;
- confundir texto JEED com valor authoritative do efeito;
- resource reload manter cache stale;
- assumir EMI/REI ativos apenas porque são suportados upstream.
## 11. Matriz de testes obrigatória
- [ ] Cliente inicia com JEED 2.3.2 + JEI 19.56.0.440.
- [ ] Plugin aparece no JEI sem registration/API error.
- [ ] Efeitos vanilla exibem descrição legível.
- [ ] Amostra de efeitos modded do pack abre sem crash/missing component crítico.
- [ ] Troca de idioma/resource reload atualiza descrição.
- [ ] JEED não altera duração/amplifier/state real dos efeitos.
- [ ] Viewer aberto/fechado repetidamente não deixa UI stale.
- [ ] Update futuro do JEI é bloqueado até smoke test do plugin.
## 12. Evidências e limites
- **Modlist física:** JAR/mod id/version e JEI atual.
- **CurseForge oficial:** file 7346553, Release NeoForge 1.21.1, função JEI/REI/EMI e changelog 2.3.2.
- **Limite:** cobertura exata de cada efeito modded depende dos dados/localizações carregados; não foi inventado catálogo de descrições.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
