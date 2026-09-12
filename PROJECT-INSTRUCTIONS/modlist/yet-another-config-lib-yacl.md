# YetAnotherConfigLib (YACL)

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8185a52ed0ec4903372d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — top-level `yet_another_config_lib_v3-3.8.2+1.21.1-neoforge.jar`, mod id `yet_another_config_lib_v3`, runtime `3.8.2+1.21.1-neoforge`, mixins `yacl.mixins.json` + `yacl-fabric.mixins.json`; Fragmentum `2.4.4` contém YACL `3.6.6+1.21.1-neoforge` somente em `META-INF/jarjar/`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026” e inclui uma seção de “Revalidação física — 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, tanto o top-level YACL 3.8.2 quanto o JarJar 3.6.6 do Fragmentum 2.4.4 estão confirmados. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** YetAnotherConfigLib (YACL)
- **Arquivo JAR:** `yet_another_config_lib_v3-3.8.2+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 3.8.2+1.21.1-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, QoL
- **Função:** Biblioteca builder-based para sistemas e telas de configuração; fornece UI/options/bindings/controllers reutilizáveis, enquanto os valores e regras de gameplay pertencem aos mods consumidores.
- **Dependências:** NeoForge 1.21.1; necessária enquanto consumers atuais declararem YACL. Top-level instalado 3.8.2; Fragmentum 2.4.4 embarca YACL 3.6.6 em META-INF/jarjar/, sem constituir segundo top-level.
- **Sobreposição:** Não é drop-in replacement de Cloth Config ou outras libraries. Consumers compilam contra APIs específicas; coexistência é normal quando requerida.
- **Compatibilidade/Riscos:** Riscos: API/version drift, client-only classloading em dedicated server, substituir incorretamente por Cloth Config e interpretar top-level 3.8.2 + YACL 3.6.6 embarcado pelo Fragmentum como conflito sem verificar resolução do loader. Config server-side continua authority do consumer.
- **Observações:** Mod id `yet_another_config_lib_v3`, runtime top-level `3.8.2+1.21.1-neoforge`. Fragmentum 2.4.4 embarca a cópia 3.6.6; não remover/forçar versão sem validar a resolução real do loader e os consumers.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial YACL 3.8.2 para NeoForge 1.21.1 + hierarquia física Fragmentum 2.4.4 → `META-INF/jarjar/yet-another-config-lib-3.6.6+1.21.1-neoforge.jar`. A cópia 3.6.6 continua aninhada, não top-level; resolução runtime entre versões não foi testada.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yacl
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — YACL top-level 3.8.2+1.21.1-neoforge permanece exatamente instalado e continua a release NeoForge 1.21.1 pertinente; authority de config, client/server boundary e cópia jar-in-jar 3.6.6 do Fragmentum 2.4.4 preservadas.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico top-level: `yet_another_config_lib_v3-3.8.2+1.21.1-neoforge.jar`, mod id `yet_another_config_lib_v3`, versão `3.8.2+1.21.1-neoforge`. YetAnotherConfigLib v3 (YACL) é uma **biblioteca builder-based para sistemas/telas de configuração**. Não cria gameplay próprio e não é substituível automaticamente por Cloth Config ou outra config library, porque consumers compilam contra APIs específicas.

## 1. Identidade e função
YACL fornece infraestrutura reutilizável para mods declararem categorias, opções, bindings, controllers e telas de configuração em vez de implementarem todo o frontend/config workflow do zero.

A build top-level instalada `3.8.2+1.21.1-neoforge` pertence à linha correta para Minecraft 1.21.1 / NeoForge.

## 2. Authority e ownership
YACL é authority somente de sua API/UI de configuração. Os **valores de gameplay e sua semântica pertencem ao mod consumidor**.

Consequências:
- uma opção exibida por YACL não se torna estado próprio da library;
- validação, defaults e efeitos de uma configuração precisam seguir o consumer;
- mods próprios não devem ler widgets/UI como substituto do config backend real;
- alterações server-authoritative não podem ser aceitas só porque o cliente exibiu/salvou uma tela.

## 3. Client/server boundary
O projeto é distribuído para Client & Server, mas grande parte da superfície visível é client-facing.

Regras de engenharia:
- dedicated server não pode carregar classes exclusivamente gráficas por uma integração própria;
- configs server/common continuam sob a authority do consumer e do mecanismo de sync correspondente;
- não enviar mutation de gameplay arbitrária a partir de callback de UI sem validação server-side.

## 4. Relação com outras config libraries
O pack também possui **Cloth Config** e outras libraries de UI/config. Isso não torna YACL redundante.

Cada consumer pode depender de uma API concreta. Substituir/remover uma library só é seguro após mapear o dependency graph real e confirmar que nenhum mod exige suas classes/runtime.

## 5. Cópia jar-in-jar 3.6.6 observada
A modlist física também registra uma cópia **aninhada** `yet-another-config-lib-3.6.6+1.21.1-neoforge.jar` dentro de `fragmentum-neoforge-1.21.1-2.4.4.jar` (**Fragmentum 2.4.4**). A hierarquia física é explícita: a entrada YACL 3.6.6 aparece em `META-INF/jarjar/` imediatamente sob o JAR top-level do Fragmentum.

Isso não constitui um segundo mod top-level nem prova conflito com a 3.8.2 instalada. Jar-in-jar pode ser usado como dependência empacotada/resolução do loader.

Boundary de manutenção:
- não deletar o top-level 3.8.2 apenas porque Fragmentum embarca a 3.6.6;
- não forçar exclusão/override da 3.6.6 sem verificar a resolução efetiva do loader e o contrato de Fragmentum;
- se surgir linkage/version conflict, identificar qual versão foi selecionada/carregada em runtime antes de intervir.

## 6. Persistência e lifecycle
Config screens normalmente refletem valores persistidos pelo consumer. Validar quando pertinente:
- abrir tela;
- alterar opção;
- aplicar/cancelar;
- reiniciar cliente/servidor;
- reconectar;
- config reload quando suportado;
- world/server config distinta de client config;
- update de YACL e consumer em conjunto.

## 7. Boundary para mods próprios e RPG Skill Tree
YACL não fornece progressão, atributo, perk, Mastery ou evento causal de gameplay.
- não conceder Mastery por abrir/salvar config;
- não usar presença da library como proxy de presença de um sistema de gameplay específico;
- uma perk dependente de config deve ler o valor autoritativo do consumer, não a UI YACL.

## 8. Riscos
1. **API/version drift:** consumer compilado contra outra linha pode falhar por method/class mismatch.
2. **Client-only leakage:** tela/controller referenciado em common/dedicated-server path.
3. **Library substitution:** Cloth Config ou outra lib tratada incorretamente como drop-in replacement.
4. **Jar-in-jar ambiguity:** top-level 3.8.2 e cópia aninhada 3.6.6 interpretadas como conflito sem verificar resolução real.
5. **Config authority confusion:** UI local usada como fonte de verdade para regra server-side.

## 9. Matriz de testes
- [ ] Dedicated server inicia com YACL 3.8.2 e consumers atuais sem client-class crash.
- [ ] Cliente abre telas YACL dos consumers relevantes.
- [ ] Alterar/aplicar/cancelar opções preserva semântica do consumer.
- [ ] Restart mantém valores persistidos corretamente.
- [ ] Config server/common não é sobrescrita por cliente sem autorização.
- [ ] Consumers não apresentam `NoSuchMethodError`/`ClassNotFoundException` após update da library.
- [ ] Runtime/logs identificam versão efetivamente usada quando consumer também contém jar-in-jar 3.6.6.
- [ ] Remoção do top-level não é tentada sem dependency graph e smoke test.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências
- **Modlist física 08/09/2026:** top-level `yet_another_config_lib_v3-3.8.2+1.21.1-neoforge.jar`, mod id `yet_another_config_lib_v3`, runtime `3.8.2+1.21.1-neoforge`; também há registro de `yet-another-config-lib-3.6.6+1.21.1-neoforge.jar` como JAR aninhado, não top-level.
- CurseForge oficial YACL: biblioteca builder-based para configuração; linha 3.8.2 compatível com Minecraft 1.21.1/NeoForge.
- Guia Gameplay: YACL é infraestrutura de config e não substitui automaticamente Cloth Config.

## 11. Limitação
Não foi feito dependency graph completo dos consumers nem validada a resolução runtime entre o YACL top-level 3.8.2 e a cópia 3.6.6 embarcada pelo Fragmentum 2.4.4. Qualquer remoção, exclusão de dependency ou forced version resolution exige inspeção do loader/runtime antes de mudança física.

## 12. Revalidação física — 11/09/2026
A modlist física mantém exatamente `yet_another_config_lib_v3-3.8.2+1.21.1-neoforge.jar`, mod id `yet_another_config_lib_v3`, versão `3.8.2+1.21.1-neoforge`. A file page oficial confirma 3.8.2 como release NeoForge suportada para Minecraft 1.21.1.

Fragmentum `2.4.4` continua contendo `META-INF/jarjar/yet-another-config-lib-3.6.6+1.21.1-neoforge.jar`. Essa entrada permanece jar-in-jar e não constitui segundo top-level. A decisão **Sem decisão** e o estado **Instalado — Dossiê completo** foram preservados. Nenhum dependency graph completo, loader-resolution, config UI ou dedicated-server test foi executado nesta recatalogação.
