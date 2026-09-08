# Cloth Config v15 API

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8187bd0bed9382336e24  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Cloth Config v15 API
- **Arquivo JAR:** `cloth-config-15.0.140-neoforge.jar`
- **Versão 1.21.1:** `15.0.140`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cloth-config
- **Função:** Config screen/API reutilizável para mods consumidores construírem interfaces de configuração com entries, categorias e widgets; a library não define por si só a semântica dos configs dos consumers.
- **Dependências:** Biblioteca de infraestrutura. A release NeoForge 15.0.140 é publicada para Minecraft 1.21/1.21.1; necessidade determinada pelos consumers instalados. Não é substituível automaticamente por ForgeConfigScreens/Configured/outras config libraries.
- **Compatibilidade/Riscos:** Riscos principais são version drift de consumers, client-only screen code em dedicated server, valores de UI divergentes do config real e duas config APIs tentando controlar a mesma screen. 15.0.140 não possui changelog de gameplay publicado; não inventar novas APIs específicas.
- **Sobreposição:** Pode coexistir com outras config libraries porque consumers compilam contra APIs específicas. Similaridade de função não implica substituição binária.
- **Observações:** mod id `cloth_config`; runtime 15.0.140. Config UI é apresentação; validação, persistência e sync server-side continuam sendo responsabilidade do consumer/config backend.
- **Procedência:** Modlist física mais recente de 07/09/2026 + CurseForge/Modrinth oficiais Cloth Config 15.0.140 NeoForge + source oficial shedaniel/cloth-config para o contrato geral de config screen API.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Cloth Config v15 API 15.0.140 foi reconfirmado como biblioteca de configuração instalada. A presença física não foi convertida em decisão de manter/remover.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — config-screen consumer contract, UI vs backend authority, side/lifecycle, version drift e coexistência com outras config libraries catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `cloth-config-15.0.140-neoforge.jar`, mod id `cloth_config`, runtime `15.0.140`, NeoForge 1.21.1. Cloth Config é uma **config screen API/library**; não é authority dos valores de gameplay dos mods consumidores.

## 1. Papel e authority

Cloth Config fornece primitives/builders/widgets para consumers criarem telas de configuração. O consumer continua definindo:

- quais opções existem;
- defaults e ranges;
- onde são persistidas;
- se são client/common/server;
- como são sincronizadas e validadas.

A UI não substitui o backend de config.

## 2. Consumer contract

Um mod que compila contra Cloth Config pode usar classes/interfaces específicas da versão. Por isso:

- não remover enquanto houver consumer ativo;
- não substituir por outra library apenas porque também cria config screens;
- updates devem ser smoke-tested com consumers reais.

## 3. Categories, entries e widgets

A API é voltada à construção de screens estruturadas em categorias/entries e componentes de edição. O catálogo não congela uma lista de classes/métodos da 15.0.140 porque a release não publica changelog API-specific suficiente para isso.

Para compat própria, usar a API/source pin do consumer em vez de nomes inferidos.

## 4. UI versus config state

Editar um valor na tela só é efetivo quando o consumer aceita/persiste a mudança. Um widget pode exibir valor local temporário antes de save/apply.

Não ler a screen como authority de gameplay; integrações devem consultar o config/provider real.

## 5. Client/common/server

Screens e widgets são client presentation. O artefato pode estar presente em ambiente Client & Server por causa do contrato de mod/library, mas dedicated server não deve carregar classes exclusivamente gráficas em common bootstrap.

Server config continua sendo validado pelo consumer/backend e não por um cliente arbitrário.

## 6. Config sync

Cloth Config por si só não autoriza assumir um protocolo universal de sync. Alguns consumers usam configs puramente client-side; outros usam backend próprio para common/server.

Qualquer mod próprio que precise reagir a configuração deve observar o consumer específico, não Cloth Config genericamente.

## 7. Coexistência com outras config APIs

Pack grande pode conter Configured, Forge config screens, YetAnotherConfigLib ou outras libraries. Elas podem coexistir quando consumers diferentes as requerem.

Conflito real existe se duas integrações tentam substituir a mesma screen/config flow do mesmo consumer, não por mera presença dos JARs.

## 8. Lifecycle

Validar abertura/fechamento da screen, save/cancel/reset, mudança de idioma, UI scale, resource reload e reconnect quando o consumer sincroniza config.

Telas não podem manter referência stale a world/server ou aplicar valores ao consumer errado depois de trocar de mundo.

## 9. Version drift

Sintomas típicos:

- linkage errors no consumer;
- screen não abrindo;
- widget ausente/incompatível;
- callback/save quebrado;
- classes client-only carregadas no servidor.

Diagnóstico deve identificar qual consumer chamou a API e qual versão foi compilada/testada.

## 10. Riscos

1. Remover library com consumer ativo.
2. Atualizar Cloth Config sem validar consumers.
3. Tratar valor visual como server authority.
4. Client classloading no dedicated server.
5. Duas config screens competindo pelo mesmo consumer.
6. Save/cancel semantics divergirem do backend real.
7. Integração própria presumir sync universal inexistente.

## 11. Matriz de testes

1. Dedicated server boot com a library presente.
2. Client boot e abrir screens de vários consumers Cloth Config.
3. Alterar/save/reabrir valores client-side.
4. Cancelar alteração e confirmar rollback esperado pelo consumer.
5. Server/common config via consumer que ofereça esse fluxo.
6. UI scale/idioma/resource reload.
7. Reconnect após mudança de config sincronizada.
8. Confirmar ausência de linkage errors em consumers após update.

## 12. Evidência

- modlist física atual: Cloth Config v15 API 15.0.140;
- CurseForge/Modrinth oficiais: release NeoForge para 1.21/1.21.1;
- projeto/source oficial: configuration/config-screen library para mods consumidores;
- ausência de changelog específico da 15.0.140 tratada fail-closed, sem inventar delta API.

> ⚙️ Boundary canônico: Cloth Config **renderiza/organiza edição de configuração**; significado, persistência e autoridade de cada opção pertencem ao mod consumidor.
