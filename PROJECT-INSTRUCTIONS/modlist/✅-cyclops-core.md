# Cyclops Core

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81769ec6fe9b401e08b9
- **Estado no pack:** Integrado ao Github
- **Autoridade física:** `cyclopscore-1.21.1-neoforge-1.29.4-1137.jar`, mod id `cyclopscore`, runtime `1.29.4`
- **Auditoria de migração Notion → GitHub:** 2026-09-14

## Propriedades do banco

- **Mod:** Cyclops Core
- **Arquivo JAR:** `cyclopscore-1.21.1-neoforge-1.29.4-1137.jar`
- **Versão 1.21.1:** 1.29.4
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Core/API do ecossistema CyclopsMC com infraestrutura compartilhada para mods consumidores, incluindo GUI/widget, config/network/data/registry utilities e outros helpers comuns.
- **Dependências:** NeoForge 1.21.1; necessidade determinada pelos consumers CyclopsMC instalados. Não remover ou substituir por outra core library enquanto dependentes ativos compilarem contra suas APIs.
- **Sobreposição:** Core específica CyclopsMC. Coexistência com outras GUI/config/network/core libraries não implica redundância; consumers dependem de contracts próprios.
- **Compatibilidade/Riscos:** Riscos de ABI/version drift em GUI/widgets, networking, data/NBT paths e helpers de consumers. A build física 1.29.4-1137 inclui os fixes #237/#236 catalogados; upstream 1.30.0 para NeoForge 1.21.1 é posterior e NÃO é presumido presente. Update de core exige teste conjunto dos consumers CyclopsMC.
- **Observações:** mod id `cyclopscore`; runtime físico 1.29.4; JAR instalado inclui build suffix `-1137`. A linha oficial NeoForge 1.21.1 publicou 1.30.0 em 11/09/2026, portanto há update disponível. Changelog granular da 1.30.0 não foi atribuído sem fonte versionada específica; fixes #237/#236 continuam evidência da build 1.29.4 instalada.
- **Procedência:** modlist.txt física atual de 11/09/2026 — 595 entradas totais incluindo o modloader — confirma `cyclopscore-1.21.1-neoforge-1.29.4-1137.jar` / runtime 1.29.4. CurseForge oficial revalidado em 12/09/2026 mostra upstream Release 1.30.0 para NeoForge 1.21.1 publicada em 11/09/2026. Runtime instalado e upstream latest permanecem separados fail-closed.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cyclops-core/files/8813417
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — lote físico #215: cyclopscore-1.21.1-neoforge-1.29.4-1137.jar / runtime 1.29.4 confirmados como instalados, mas não mais latest. Upstream NeoForge 1.21.1 avançou para 1.30.0 em 11/09/2026; update disponível sem alterar a authority física do pack.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Cyclops Core foi reconciliado pela autoridade máxima: a modlist física atual confirma `cyclopscore-1.21.1-neoforge-1.29.4-1137.jar` / runtime 1.29.4. A referência 1.29.3 permanece apenas como evidência histórica desatualizada.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ⚙️ Autoridade física atual: `cyclopscore-1.21.1-neoforge-1.29.4-1137.jar`, mod id `cyclopscore`, runtime `1.29.4`. A referência 1.29.3 em guia temático está desatualizada e **não** sobrepõe a modlist física atual de 11/09/2026.

## 1. Papel e authority
Cyclops Core é a core library do ecossistema CyclopsMC. Consumers usam suas utilities de GUI/widgets, data/config, networking, registries e outros helpers; o consumer permanece authority de máquinas, energia, logic networks, mobs ou gameplay final.

## 2. Consumer-driven necessity
A necessidade do JAR é definida pelo dependency graph dos mods CyclopsMC instalados. Remover a library com consumer ativo pode impedir bootstrap ou produzir linkage errors.
Outra core library não é substituto automático, mesmo quando oferece GUI/network/config helpers semelhantes.

## 3. GUI e widgets
A linha 1.29.x possui correções concretas em GUI/widgets, comprovando que essa é uma superfície central da library.
A 1.29.3 corrigiu GUIs que não abriam quando o jogador era forçado a uma pose agachada (#233). Esse histórico continua relevante como regression context, mas a build atual é 1.29.4.

## 4. Infobook — fix 1.29.4
A 1.29.4 corrige **crash ao passar o mouse no canto superior esquerdo de recipe pages do infobook** (#237).
Regression gate: recipe pages precisam abrir, aceitar hover nos limites da interface e fechar/reabrir sem exception.

## 5. Scrolling container — fix 1.29.4
A release também corrige o scrolling container para alcançar a **última linha de grids multi-coluna** (#236).
Essa correção é UI/layout, mas pode afetar acesso funcional a entries que antes ficavam inacessíveis. Testar listas grandes em consumers reais.

## 6. Networking
Cyclops Core fornece infraestrutura de networking usada pelos consumers. Packet semantic e authority pertencem ao consumer.
Handlers devem validar contexto/side e não executar a mesma ação duas vezes em retry/reconnect. Atualizações de core podem afetar codec/dispatch mesmo sem alterar gameplay diretamente.

## 7. Data, config e registries
A library fornece utilities para dados/config/registration usados pelo ecossistema. Schema/defaults e conteúdo final pertencem ao consumer.
Reload ou version migration não deve deixar IDs/caches stale nem re-registrar entradas.

## 8. NBT/data expressions
Versões recentes da linha 1.21.1 adicionaram/ajustaram helpers de expressão/NBT path. Esta ficha usa esse histórico apenas para delimitar a superfície técnica da core; não atribui syntax/API concreta da 1.29.4 além do que o upstream publica.
Para código próprio, pin do source/JAR deve preceder uso de símbolos específicos.

## 9. Client/server
Cyclops Core é infraestrutura comum. GUI/render é client-facing; networking/data/config e gameplay-support podem operar em ambos os lados conforme consumer.
Dedicated server não deve carregar screens/widgets para inicializar common services.

## 10. Lifecycle
Validar construction/bootstrap, consumer registration, client join, GUI open/close, datapack/config reload quando aplicável, disconnect/reconnect e server restart.
Widget/container state não deve sobreviver de forma incorreta a screen recreation ou troca de mundo.

## 11. Version drift e upstream posterior
A versão fisicamente instalada continua sendo **1.29.4** no JAR `cyclopscore-1.21.1-neoforge-1.29.4-1137.jar`, porém o upstream publicou **Cyclops Core 1.30.0 para NeoForge 1.21.1 em 11/09/2026**. A 1.30.0 é um update disponível, não comportamento já presente no pack.

Como não foi confirmado nesta auditoria um changelog granular específico da 1.30.0 para esta linha, nenhuma classe, API ou correção nova é retroprojetada para a build instalada.

Sintomas possíveis de version drift:
- `NoSuchMethodError`/`NoClassDefFoundError`;
- GUI que não abre;
- infobook crash;
- scroll/container layout incorreto;
- packet/data mismatch;
- consumer que inicia parcialmente.

Diagnóstico deve registrar **Cyclops Core instalado 1.29.4-1137**, consumer, ação exata e distinguir esse runtime do **upstream 1.30.0**.

## 12. Reconciliação de versão
O banco chegou a conter menção residual de 1.29.3. A autoridade física atual resolve o runtime do pack: **a modlist de 11/09/2026 contém `cyclopscore-1.21.1-neoforge-1.29.4-1137.jar` / runtime 1.29.4** dentro das 595 entradas totais incluindo o modloader.

Isso não significa que 1.29.4 continue latest: a publicação oficial de 11/09/2026 já oferece **1.30.0 para NeoForge 1.21.1**. O catálogo mantém simultaneamente os dois fatos: **instalado = 1.29.4-1137; upstream latest = 1.30.0**.

## 13. Riscos
1. Remover core com consumer ativo.
2. Atualizar core isoladamente e quebrar ABI.
3. Infobook/recipe page regredir (#237).
4. Grid multi-coluna esconder última linha (#236).
5. Forced-crouch GUI regression histórica (#233).
6. Client GUI class carregar no dedicated server.
7. Packet/data handler duplicar state.
8. Guia desatualizado substituir equivocadamente a versão física.
9. Upstream 1.30.0 ser tratado como já instalado, ou update da core ser aplicado sem validar os consumers.

## 14. Matriz de testes
1. Dedicated server boot com consumers Cyclops atuais.
2. Client join sem linkage errors.
3. Abrir GUIs dos consumers em pose normal e forced crouching.
4. Infobook recipe pages: hover em bordas/canto superior esquerdo — regression #237.
5. Grid multi-coluna grande: alcançar última linha — regression #236.
6. Network actions dos consumers exactly once.
7. Config/data reload quando aplicável.
8. Disconnect/reconnect/restart sem duplicate registration.
9. Testar upgrade 1.29.4-1137 → 1.30.0 em cópia do pack, validando bootstrap, GUIs, networking e consumers antes de alterar a modlist física.

## 15. Evidência
- modlist física atual de 11/09/2026: `cyclopscore-1.21.1-neoforge-1.29.4-1137.jar` / Cyclops Core runtime 1.29.4;
- CurseForge oficial 1.29.4: fixes #237 e #236;
- CurseForge oficial revalidado em 12/09/2026: Release 1.30.0 para NeoForge 1.21.1 publicada em 11/09/2026, posterior ao runtime instalado;
- changelog anterior 1.29.3: forced crouching GUI fix #233;
- detalhes funcionais da 1.30.0 não confirmados permanecem fail-closed.

> 🔒 Boundary canônico: **Cyclops Core fornece infraestrutura; a modlist física define sua versão; consumers mantêm authority do gameplay final**.