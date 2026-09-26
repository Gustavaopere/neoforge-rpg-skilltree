# PartiCull

## Propriedades do registro

- **Mod:** PartiCull
- **Arquivo JAR:** PartiCull-neoforge-1.21.1-v2.0.jar
- **Versão 1.21.1:** 2.0
- **Categoria:** Visual, QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://modrinth.com/mod/particull
- **Função:** Otimização client-side que monitora FPS e reduz partículas quando o desempenho cai, restaurando-as quando estabiliza; atua na visibilidade/renderização, não no state lógico dos eventos.
- **Dependências:** Cliente NeoForge 1.21.1. Não foi confirmada hard dependency externa para v2.0. Config acessível pela UI de Mods; Target FPS é a opção explicitamente documentada.
- **Compatibilidade/Riscos:** Pode cullar feedback visual importante de spells/combate; testar com Particle Effects, Particle Rain, Particular e Iris. O ganho upstream de até \~30% é contextual e não foi reproduzido localmente. Verificar oscilação perto do Target FPS e recovery após stress.
- **Sobreposição:** Otimiza/culla a saída de Particle Effects, Particle Rain, Particular e outros providers visuais; não substitui esses mods nem seus estados lógicos.
- **Observações:** mod id `particull`; runtime 2.0. Client-side. A documentação auditada afirma que a config tem três opções, mas só nomeia explicitamente Target FPS; os demais campos não foram inventados.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + Modrinth/CurseForge oficiais da v2.0.
- **Atualização/Status:** REVALIDADO EM 25/09/2026 — PartiCull 2.0/JAR físico reconfirmado; client-side performance boundary e riscos de culling preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #437: JAR `PartiCull-neoforge-1.21.1-v2.0.jar`, mod id `particull`, runtime `2.0`, SHA-1 `f84fa9feecf97462e248b6d3ad0335a90cb6e96c`.

<callout icon="⚡" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `PartiCull-neoforge-1.21.1-v2.0.jar`, mod id `particull`, versão `2.0`. PartiCull é **otimização client-side de partículas baseada em FPS**; não cria efeitos de gameplay nem decide estados server-side.
</callout>
## 1. Identidade, versão e papel
- **Mod:** PartiCull / PartiCull: Particle Fix.
- **JAR físico:** `PartiCull-neoforge-1.21.1-v2.0.jar`.
- **Mod id:** `particull`.
- **Versão instalada:** `2.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client.
- **Papel:** observar desempenho/FPS do cliente e reduzir partículas quando o framerate cai, restaurando-as quando o desempenho volta a estabilizar.
## 2. Authority e ownership
PartiCull controla somente **visibilidade/quantidade efetivamente renderizada de partículas no cliente**. Ele não deve modificar o state lógico que originou a partícula: efeitos, projéteis, clima, ataques, spells e eventos continuam pertencendo aos providers originais.
Assim, uma partícula removida por PartiCull não significa que o evento correspondente deixou de existir. Telegraphs de combate e sinais visuais críticos precisam ser testados para garantir legibilidade sob culling agressivo.
## 3. Algoritmo publicado
A descrição oficial afirma que o mod monitora continuamente o desempenho do jogo. Quando detecta queda de framerate, remove partículas para manter fluidez; quando o desempenho estabiliza, volta a permitir mais partículas.
O upstream divulga ganhos de até cerca de 30% em cenários pesados de partículas. Esse número é **resultado promocional/contextual**, não ganho garantido do modpack e não foi reproduzido nesta auditoria.
## 4. Configuração
A configuração é acessível no cliente por `Esc → Mods → PartiCull → Config`.
A documentação informa **três opções** na UI, mas publica explicitamente por nome apenas o **Target FPS**, que pode sincronizar automaticamente com o monitor e é recomendado pelo autor como valor geralmente deixado no automático.
Como os outros dois nomes/semânticas não foram publicados no trecho oficial auditado, esta ficha não os inventa. A configuração local do usuário também não foi lida.
## 5. Client / server
- Client-side: pode ser usado em singleplayer ou em servidores sem exigir alteração de regras do servidor.
- O servidor continua processando os eventos normais; PartiCull apenas decide o que o cliente desenha.
- Dedicated server não deve depender deste mod para gameplay.
## 6. Lifecycle
Validar:
- entrada no mundo com FPS alto/baixo;
- transição entre carga leve e cena com muitas partículas;
- troca de dimensão;
- resource reload;
- mudança de Target FPS durante a sessão;
- relog/restart preservando config;
- resolução/refresh-rate diferentes.
O comportamento deve ser gradual/reversível: após a carga diminuir, partículas não devem permanecer permanentemente suprimidas por state stale.
## 7. Integrações concretas no pack
- **Particle Effects 1.5.0:** provider visual de partículas por efeito; PartiCull pode reduzir essa saída.
- **Particle Rain / Particular:** aumentam significativamente a densidade de partículas em clima/ambiente; são os principais cenários de stress.
- **Iris/shaders:** shaders podem elevar custo por partícula via blending/overdraw, fazendo o culling entrar mais cedo.
- **Epic Fight / Iron's / Ars / outros VFX-heavy mods:** telegraphs e projéteis podem usar partículas como feedback. Culling não pode tornar combate ilegível.
- **Obscure Tooltips:** partículas de UI estão em contexto distinto; testar para confirmar que o culling não interfere indevidamente em HUD/tooltips.
## 8. Riscos técnicos
1. **Telegraph loss:** partículas funcionalmente importantes podem ficar visualmente reduzidas em combate.
2. **Oscilação:** FPS perto do alvo pode causar comportamento de remove/restore perceptível; testar estabilidade.
3. **Over-culling com shaders:** custo alto de Iris/shader pode reduzir partículas mesmo quando a causa do frame drop não é o particle system.
4. **Interação com outros optimizers:** múltiplos mods podem alterar a mesma etapa de particle render/tick.
5. **Config/display refresh:** Target FPS automático deve acompanhar corretamente monitor/VSync/refresh rate.
6. **Marketing vs runtime:** não assumir +30% sem benchmark local reproduzível.
## 9. Multiplayer
Dois jogadores podem ver quantidades diferentes de partículas dependendo de FPS/config local, mantendo o mesmo state de jogo. Isso é aceitável enquanto telegraphs essenciais permanecem suficientemente claros.
## 10. Matriz de testes
- [ ] Cliente inicia com v2.0 e config UI abre.
- [ ] Target FPS automático corresponde ao monitor esperado.
- [ ] Cena leve mantém partículas normalmente.
- [ ] Stress intenso reduz partículas sem crash/stutter adicional.
- [ ] Após recuperar FPS, partículas retornam.
- [ ] Particle Effects + Particular + Particle Rain simultâneos.
- [ ] Iris/shader pesado e shader desligado para comparar limiar.
- [ ] Epic Fight/spells: telegraphs continuam legíveis.
- [ ] Troca de dimensão/relog sem culling preso.
- [ ] Multiplayer: clientes com FPS distintos mantêm gameplay sincronizado.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 11. Evidências e limites
- Modlist física canônica atual de 25/09/2026: JAR, mod id e versão.
- CurseForge/Modrinth oficiais: NeoForge 1.21.1 v2.0, ambiente Client e descrição do controle dinâmico conforme FPS.
- Documentação oficial: config in-game com três opções e Target FPS sincronizado ao monitor.
- **Limite:** o ganho upstream de até cerca de 30% é contextual e não foi reproduzido nesta instância; dois campos da config não foram nomeados pela documentação auditada e não foram inventados.
