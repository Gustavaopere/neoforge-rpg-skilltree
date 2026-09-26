# Potentials

## Propriedades do registro

- **Mod:** Potentials
- **Arquivo JAR:** potentials-neoforge-1.21-0.7.1.jar
- **Versão 1.21.1:** 0.7.1
- **Categoria:** Biblioteca, Compat, Tecnologia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/potentials
- **Função:** Biblioteca/API cross-platform para expor e padronizar capabilities/capacidades usadas por mods consumidores em loaders modernos.
- **Dependências:** NeoForge 1.21.x; necessidade depende de consumers. Nenhum consumer causal atual foi comprovado no catálogo neste passe.
- **Compatibilidade/Riscos:** Library Beta sem gameplay próprio. Riscos: consumer/API drift, negative-energy edge case, stale capability/cache e false redundancy com outras APIs. Não comparar 0.8.x/0.9.x de linhas posteriores como upgrades 1.21.1.
- **Sobreposição:** Camada de capability cross-platform; não substitui APIs nativas de energia/fluidos nem outras libraries por categoria semelhante.
- **Observações:** Runtime 0.7.1, Beta NeoForge compatível com 1.21–1.21.4. Delta exato: fix de crash ao inserir/extrair energia negativa.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + CurseForge oficial Potentials 0.7.1 + cruzamento atual do catálogo.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — Potentials 0.7.1/JAR físico reconfirmado; consumer causal continua não comprovado e decisão Sem decisão preservada.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #453: JAR `potentials-neoforge-1.21-0.7.1.jar`, mod id `potentials`, runtime `0.7.1`, SHA-1 `c2525c00049e1773a813ed240ac96914c01e0b3f`.

<callout icon="🔎" color="yellow_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `potentials-neoforge-1.21-0.7.1.jar`, mod id `potentials`, versão `0.7.1`, NeoForge 1.21.x. Potentials é uma library/API cross-platform de capabilities; o projeto afirma explicitamente que **não faz nada sozinho**. Nenhum consumer causal atual foi comprovado no catálogo, então a decisão permanece **Sem decisão**.
</callout>
## 1. Identidade e papel
- **Mod:** Potentials.
- **JAR físico:** `potentials-neoforge-1.21-0.7.1.jar`.
- **Mod id:** `potentials`.
- **Runtime:** `0.7.1`.
- **Loader/jogo:** NeoForge; publicação cobre 1.21–1.21.4, incluindo 1.21.1.
- **Canal:** Beta.
- **Licença:** MIT.
- **Ambiente:** Client & Server.
- **Papel:** abstrair lookups/capabilities entre loaders para consumers.
- **Decisão:** Sem decisão.
## 2. Library sem gameplay próprio
A descrição oficial é inequívoca: Potentials não fornece gameplay autônomo. Sua utilidade depende de consumers que usem sua API.
Isso implica duas regras de auditoria:
- não promover APIs disponíveis a features ativas sem consumer;
- não remover a library só porque nenhum item/bloco aparece no jogo.
## 3. Capabilities publicadas
A documentação lista implementações comuns para:
- energia em blocks/block entities;
- energia em items;
- fluid storage em blocks/block entities;
- fluid storage em items.
Potentials fornece contratos/lookup; geração, consumo, capacidade e balanceamento pertencem aos mods consumidores.
## 4. Boundary cross-platform
O propósito é reduzir diferenças entre NeoForge e Fabric na descoberta/uso de capabilities. Isso não significa que APIs nativas de NeoForge deixem de existir nem que Potentials substitua FE/fluid handlers de todos os mods.
Um consumer precisa chamar/depender de Potentials explicitamente para tornar a library load-bearing.
## 5. Build 0.7.1
A build física é a Beta pertinente à linha Minecraft 1.21.1. Releases 0.8.x/0.9.x visíveis numericamente pertencem a linhas posteriores de Minecraft e não são upgrades diretos para este pack.
O delta específico de 0.7.1 corrige **crash ao inserir/extrair energia negativa**.
## 6. Energia negativa como regression gate
Valores negativos são um edge case crítico para APIs energéticas. O teste deve garantir que consumer inválido ou cálculo intermediário não cause crash nem dupe de energia.
O fix não autoriza valores negativos como gameplay; apenas documenta a superfície corrigida.
## 7. Consumer mapping atual
A busca no catálogo por `Potentials` retornou apenas a própria página. Isso não prova ausência de consumer no conjunto físico, pois manifests/classes das 587 entradas top-level da modlist física atual, incluindo o modloader, não foram varridos nesta etapa.
Conclusão fail-closed:
- presença física = confirmada;
- função = confirmada;
- consumer = não comprovado;
- remoção segura = não comprovada;
- decisão = `Sem decisão`.
## 8. Client/server e networking
Capability funcional de energia/fluidos deve ser server-authoritative. Potentials não deve permitir que cliente determine armazenamento ou transferência real.
Dedicated server precisa carregar sem classes client-only vazando por abstrações cross-platform.
## 9. Lifecycle
Testar consumers identificados em:
- attach/lookup de capability;
- chunk unload/reload;
- item transfer entre inventories;
- block break/place;
- dimension change;
- server restart;
- optional-mod presence/absence.
Caches/lookups não podem reter referência inválida após provider ser removido.
## 10. Sobreposição
Potentials não é substituto automático de NeoForge Energy, Fabric Transfer API, Architectury ou outras libraries. O contrato exigido pelo consumer define necessidade.
Múltiplas abstraction libraries podem coexistir porque atendem APIs/mod IDs diferentes.
## 11. Riscos
1. **Consumer desconhecido:** remoção pode quebrar mod ainda não mapeado.
2. **Beta:** build 0.7.1 é prerelease para esta linha.
3. **API drift:** consumer pode esperar outra assinatura/semântica.
4. **Negative energy edge case:** regressão explicitamente corrigida.
5. **Loader abstraction:** comportamento pode divergir entre targets.
6. **Stale capability/cache:** provider removido/unloaded deixa referência inválida.
7. **False redundancy:** outra API de energia/fluidos não substitui mod id/contrato.
## 12. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Potentials 0.7.1.
- [ ] Dependency scan identifica consumers reais de `potentials`.
- [ ] Consumer de energia representativo insere/extrai valores normais corretamente.
- [ ] Valor negativo não reproduz crash corrigido nem duplica energia.
- [ ] Consumer de fluidos, se existir, transfere e persiste state corretamente.
- [ ] Chunk unload/reload invalida/reobtém capability sem stale reference.
- [ ] Item capability sobrevive a troca de slot/container conforme consumer.
- [ ] Optional consumer ausente não quebra bootstrap da library.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 13. Evidências e limites
- Modlist física: `potentials-neoforge-1.21-0.7.1.jar`, mod id/runtime e hash físico.
- CurseForge oficial: Beta NeoForge 0.7.1 compatível com 1.21–1.21.4, incluindo 1.21.1; Client & Server, MIT.
- Changelog 0.7.1: correção de crash ao inserir/extrair energia negativa.
- Descrição oficial: cross-platform capabilities e ausência de gameplay próprio.
- Busca atual do catálogo não encontrou consumer causal.
- **Limite:** manifests/classes de todos os JARs não foram escaneados; por isso não declarar a library órfã nem dispensável.
